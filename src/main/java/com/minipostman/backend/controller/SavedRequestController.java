package com.minipostman.backend.controller;

import com.minipostman.backend.dto.RunResultDTO;
import com.minipostman.backend.dto.SavedRequestDTO;
import com.minipostman.backend.model.Project;
import com.minipostman.backend.model.SavedRequest;
import com.minipostman.backend.service.ProjectService;
import com.minipostman.backend.service.SavedRequestService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
public class SavedRequestController {
    
    private final SavedRequestService savedRequestService;
    private final ProjectService projectService;  // ← ADD THIS
    
    // Update constructor
    public SavedRequestController(SavedRequestService savedRequestService, 
                                  ProjectService projectService) {  // ← ADD THIS
        this.savedRequestService = savedRequestService;
        this.projectService = projectService;  // ← ADD THIS
    }
    
    // Save request (FIXED - now requires projectId)
    @PostMapping("/save")
    public SavedRequest saveRequest(@RequestBody SavedRequestDTO dto) {
        return savedRequestService.saveFromDTO(dto);
    }
    
    // NEW: Create a project
    @PostMapping("/projects")
    public Project createProject(@RequestBody Project project) {
        return projectService.createProject(project.getName());
    }
    
    // NEW: Get all projects (for dropdown)
    @GetMapping("/projects")
    public List<Project> getAllProjects() {
        return projectService.getAllProjects();
    }
    
    // NEW: Get project by ID
    @GetMapping("/projects/{id}")
    public Project getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }
    
    // Run all requests in a project
    @GetMapping("/projects/{id}/run")
    public List<RunResultDTO> runAll(@PathVariable Long id) {
        return savedRequestService.runAllByProject(id);
    }
    
    // Get all saved requests
    @GetMapping("/requests")
    public List<SavedRequest> getAllRequests() {
        return savedRequestService.getAllRequests();
    }
    
    // NEW: Get requests by project
    @GetMapping("/projects/{id}/requests")
    public List<SavedRequest> getRequestsByProject(@PathVariable Long id) {
        return savedRequestService.getRequestsByProject(id);
    }
    
    @DeleteMapping("/requests/{id}")
    public void deleteRequest(@PathVariable Long id) {
        savedRequestService.deleteRequest(id);
    }
    
    @PutMapping("/requests/{id}")
    public SavedRequest updateRequest(@PathVariable Long id, @RequestBody SavedRequestDTO dto) {
        return savedRequestService.updateRequest(id, dto);
    }
}