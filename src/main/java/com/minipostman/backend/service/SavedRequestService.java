package com.minipostman.backend.service;

import com.minipostman.backend.dto.SavedRequestDTO;
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

    public SavedRequest saveFromDTO(SavedRequestDTO dto) {

        SavedRequest request = new SavedRequest();

        request.setName(dto.getName());
        request.setMethod(dto.getMethod());
        request.setUrl(dto.getUrl());
        request.setHeaders(dto.getHeaders());
        request.setBody(dto.getBody());

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
}