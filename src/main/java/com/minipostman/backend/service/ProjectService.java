package com.minipostman.backend.service;

import com.minipostman.backend.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import com .minipostman.backend.model.Project;
import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public Project createProject(String name){
        Project project = new Project();
        project.setName(name);
        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
    }
}

