package com.minipostman.backend.controller;

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
    public SavedRequest saveRequest(@RequestBody SavedRequest request) {
        return service.saveRequest(request);
    }

    
    @GetMapping("/requests")
    public List<SavedRequest> getAllRequests() {
        return service.getAllRequests();
    }
}