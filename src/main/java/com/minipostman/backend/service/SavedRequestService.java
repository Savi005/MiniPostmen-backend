package com.minipostman.backend.service;

import com.minipostman.backend.model.SavedRequest;
import com.minipostman.backend.repository.SavedRequestRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SavedRequestService {

    private final SavedRequestRepository repository;

    public SavedRequestService(SavedRequestRepository repository) {
        this.repository = repository;
    }

    // Save request
    public SavedRequest saveRequest(SavedRequest request) {
        return repository.save(request);
    }

    // Get all requests
    public List<SavedRequest> getAllRequests() {
        return repository.findAll();
    }
}