package com.minipostman.backend.service;

import com.minipostman.backend.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import com.minipostman.backend.model.Project;
import com.minipostman.backend.model.User;
import com.minipostman.backend.repository.UserRepository;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository,
                          UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    // Create project linked to user
    public Project createProject(String name, String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Project project = new Project();
        project.setName(name);
        project.setUser(user);

        return projectRepository.save(project);
    }

    // Get all projects by user
    public List<Project> getProjectsByUser(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return projectRepository.findByUser(user);
    }

    // Get project by id
    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }
}