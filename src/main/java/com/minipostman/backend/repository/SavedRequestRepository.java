package com.minipostman.backend.repository;

import com.minipostman.backend.model.SavedRequest;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SavedRequestRepository extends JpaRepository<SavedRequest, Long> {
    List<SavedRequest> findByProjectId(Long projectId);
}