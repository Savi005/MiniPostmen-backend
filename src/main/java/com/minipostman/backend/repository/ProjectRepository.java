package com.minipostman.backend.repository;
import com.minipostman.backend.model.Project;
import com.minipostman.backend.model.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByUser(User user);
    
}
