package com.minipostman.backend.service;

import com.minipostman.backend.dto.SavedRequestDTO;
import com.minipostman.backend.dto.RunResultDTO;
import com.minipostman.backend.model.Project;
import com.minipostman.backend.model.SavedRequest;
import com.minipostman.backend.repository.ProjectRepository;
import com.minipostman.backend.repository.SavedRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class SavedRequestService {

    private final SavedRequestRepository repository;
    private final ProjectRepository projectRepository;
    private final ProjectService projectService;  // ← ADD THIS
    
   
    public SavedRequestService(SavedRequestRepository repository, 
                               ProjectRepository projectRepository,
                               ProjectService projectService) { 
        this.repository = repository;
        this.projectRepository = projectRepository;
        this.projectService = projectService;  
    }

    // FIXED: Save request with project association
    public SavedRequest saveFromDTO(SavedRequestDTO dto) {
        // VALIDATION: Project ID is required
        if (dto.getProjectId() == null) {
            throw new RuntimeException("Project ID is required. Please select or create a project first.");
        }
        
        // Find the project
        Project project = projectRepository.findById(dto.getProjectId())
            .orElseThrow(() -> new RuntimeException("Project not found with ID: " + dto.getProjectId()));
        
        // Create the saved request
        SavedRequest request = new SavedRequest();
        request.setName(dto.getName());
        request.setMethod(dto.getMethod());
        request.setUrl(dto.getUrl());
        request.setHeaders(dto.getHeaders());
        request.setBody(dto.getBody());
        request.setExpectedStatus(dto.getExpectedStatus());
        request.setProject(project);  // ← CRITICAL: Link request to project
        
        return repository.save(request);
    }

    // Get all saved requests (for display)
    public List<SavedRequest> getAllRequests() {
        return repository.findAll();
    }
    
    // NEW: Get requests by project
    public List<SavedRequest> getRequestsByProject(Long projectId) {
        return repository.findByProjectId(projectId);
    }

    public void deleteRequest(Long id) {
        repository.deleteById(id);
    }

    public SavedRequest updateRequest(Long id, SavedRequestDTO dto) {
        SavedRequest existing = repository.findById(id)
            .orElseThrow(() -> new RuntimeException("Request not found"));
        
        existing.setName(dto.getName());
        existing.setMethod(dto.getMethod());
        existing.setUrl(dto.getUrl());
        existing.setHeaders(dto.getHeaders());
        existing.setBody(dto.getBody());
        
        // Update project if changed
        if (dto.getProjectId() != null && !dto.getProjectId().equals(existing.getProject().getId())) {
            Project project = projectRepository.findById(dto.getProjectId())
                .orElseThrow(() -> new RuntimeException("Project not found"));
            existing.setProject(project);
        }
        
        return repository.save(existing);
    }
    
    // FIXED: Run all requests for a project
    public List<RunResultDTO> runAllByProject(Long projectId) {
        // Get all requests belonging to this project
        List<SavedRequest> requests = repository.findByProjectId(projectId);
        
        // VALIDATION: Check if project has any requests
        if (requests.isEmpty()) {
            throw new RuntimeException("No saved requests found for project ID: " + projectId + 
                                     ". Please save some requests to this project first.");
        }
        
        List<RunResultDTO> results = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        
        // Execute each saved request
        for (SavedRequest req : requests) {
            int actualStatus = 0;
            long startTime = System.currentTimeMillis();
            
            try {
                // Prepare headers
                HttpHeaders headers = new HttpHeaders();
                // TODO: Parse JSON headers from req.getHeaders()
                
                // Prepare body
                HttpEntity<String> entity = new HttpEntity<>(
                    req.getBody() != null ? req.getBody() : "", 
                    headers
                );
                
                // Execute request
                ResponseEntity<String> response = restTemplate.exchange(
                    req.getUrl(),
                    HttpMethod.valueOf(req.getMethod()),
                    entity,
                    String.class
                );
                
                actualStatus = response.getStatusCode().value();
                
            } catch (Exception e) {
                actualStatus = 500;
                System.err.println("Error executing " + req.getMethod() + " " + req.getUrl() + ": " + e.getMessage());
            }
            
            long responseTime = System.currentTimeMillis() - startTime;
            
            // Check if test passed (actual status matches expected)
            boolean passed = req.getExpectedStatus() != null && actualStatus == req.getExpectedStatus();
            
            // Create result DTO
            RunResultDTO result = new RunResultDTO(
                req.getUrl(),
                req.getMethod(),
                req.getExpectedStatus() != null ? req.getExpectedStatus() : 0,
                actualStatus,
                passed,
                responseTime  
            );
            
            results.add(result);
        }
        
        return results;
    }
}