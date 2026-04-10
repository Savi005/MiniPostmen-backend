package com.minipostman.backend.controller;

import com.minipostman.backend.dto.RunResultDTO;
import com.minipostman.backend.dto.SavedRequestDTO;
import com.minipostman.backend.model.Project;
import com.minipostman.backend.model.SavedRequest;
import com.minipostman.backend.security.JwtUtil;
import com.minipostman.backend.service.ProjectService;
import com.minipostman.backend.service.SavedRequestService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SavedRequestController {

    private final SavedRequestService savedRequestService;
    private final ProjectService projectService;
    private final JwtUtil jwtUtil;

    public SavedRequestController(SavedRequestService savedRequestService,
                                  ProjectService projectService,
                                  JwtUtil jwtUtil) {

        this.savedRequestService = savedRequestService;
        this.projectService = projectService;
        this.jwtUtil = jwtUtil;
    }

    // Save request
    @PostMapping("/save")
    public SavedRequest saveRequest(@RequestBody SavedRequestDTO dto) {
        return savedRequestService.saveFromDTO(dto);
    }

    // Create project for logged-in user
    @PostMapping("/projects")
    public Project createProject(@RequestBody Project project,
                                 HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);

        String email = jwtUtil.extractEmail(token);

        return projectService.createProject(project.getName(), email);
    }

    // Get projects of logged-in user
    @GetMapping("/projects")
    public List<Project> getAllProjects(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        String token = authHeader.substring(7);

        String email = jwtUtil.extractEmail(token);

        return projectService.getProjectsByUser(email);
    }

    // Get project by ID
    @GetMapping("/projects/{id}")
    public Project getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    // Run all requests in project
    @GetMapping("/projects/{id}/run")
    public List<RunResultDTO> runAll(@PathVariable Long id) {
        return savedRequestService.runAllByProject(id);
    }

    // Get all saved requests
    @GetMapping("/requests")
    public List<SavedRequest> getAllRequests() {
        return savedRequestService.getAllRequests();
    }

    // Get saved requests by project
    @GetMapping("/projects/{id}/requests")
    public List<SavedRequest> getRequestsByProject(@PathVariable Long id) {
        return savedRequestService.getRequestsByProject(id);
    }

    // Delete request
    @DeleteMapping("/requests/{id}")
    public void deleteRequest(@PathVariable Long id) {
        savedRequestService.deleteRequest(id);
    }

    // Update request
    @PutMapping("/requests/{id}")
    public SavedRequest updateRequest(@PathVariable Long id,
                                      @RequestBody SavedRequestDTO dto) {
        return savedRequestService.updateRequest(id, dto);
    }
}