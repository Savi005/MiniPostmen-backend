package com.minipostman.backend.repository;

import com.minipostman.backend.model.SavedRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavedRequestRepository extends JpaRepository<SavedRequest, Long> {
}