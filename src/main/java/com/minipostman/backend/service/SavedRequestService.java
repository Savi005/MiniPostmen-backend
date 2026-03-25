package com.minipostman.backend.service;

import com.minipostman.backend.dto.SavedRequestDTO;
import com.minipostman.backend.model.Project;
import com.minipostman.backend.model.SavedRequest;
import com.minipostman.backend.repository.ProjectRepository;
import com.minipostman.backend.repository.SavedRequestRepository;
import org.springframework.stereotype.Service;
import com.minipostman.backend.dto.RunResultDTO;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.ArrayList;
import java.util.List;


@Service
public class SavedRequestService {

    private final ProjectRepository projectRepository;
    private final SavedRequestRepository repository;

    public SavedRequestService(SavedRequestRepository repository, ProjectRepository projectRepository) {
        this.repository = repository;
        this.projectRepository = projectRepository;
    }

    public SavedRequest saveFromDTO(SavedRequestDTO dto) {

        SavedRequest request = new SavedRequest();

        request.setName(dto.getName());
        request.setMethod(dto.getMethod());
        request.setUrl(dto.getUrl());
        request.setHeaders(dto.getHeaders());
        request.setBody(dto.getBody());
        request.setExpectedStatus(dto.getExpectedStatus());
        
        if (dto.getProjectId() != null) {
            Project project = projectRepository.findById(dto.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project not found"));
            request.setProject(project);
        }

        return repository.save(request);
    }

    public List<SavedRequest> getAllRequests() {
        return repository.findAll();

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

        return repository.save(existing);
    }
    public List<RunResultDTO> runAllByProject(Long projectId) {

    List<SavedRequest> requests = repository.findByProjectId(projectId);
    List<RunResultDTO> results = new ArrayList<>();

    RestTemplate restTemplate = new RestTemplate();

    for (SavedRequest req : requests) {

        int actualStatus = 0;

        try {
            HttpHeaders httpHeaders = new HttpHeaders();
            HttpEntity<String> entity = new HttpEntity<>(req.getBody(), httpHeaders);

            ResponseEntity<String> response = restTemplate.exchange(
                    req.getUrl(),
                    HttpMethod.valueOf(req.getMethod()),
                    entity,
                    String.class
            );

            actualStatus = response.getStatusCode().value();

        } catch (Exception e) {
            actualStatus = 500; 
        }

        boolean passed = req.getExpectedStatus() != null &&
                         actualStatus == req.getExpectedStatus();

        results.add(new RunResultDTO(
                req.getUrl(),
                req.getMethod(),
                req.getExpectedStatus() != null ? req.getExpectedStatus() : 0,
                actualStatus,
                passed
        ));
    }

    return results;
}
}