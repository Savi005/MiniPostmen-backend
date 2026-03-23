package com.minipostman.backend.controller;

import com.minipostman.backend.dto.SavedRequestDTO;
import com.minipostman.backend.model.SavedRequest;
import com.minipostman.backend.service.SavedRequestService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class SavedRequestController {

    private final SavedRequestService service;

    public SavedRequestController(SavedRequestService service) {
        this.service = service;
    }

    @PostMapping("/save")
    public SavedRequest saveRequest(@RequestBody SavedRequestDTO dto) {
        return service.saveFromDTO(dto);
    }

    @GetMapping("/requests")
    public List<SavedRequest> getAllRequests() {
        return service.getAllRequests();
    }

    @DeleteMapping("/requests/{id}")
    public void deleteRequest(@PathVariable Long id) {
        service.deleteRequest(id);
    }

    @PutMapping("/requests/{id}")
    public SavedRequest updateRequest(
            @PathVariable Long id,
            @RequestBody SavedRequestDTO dto) {
        return service.updateRequest(id, dto);
    }
}