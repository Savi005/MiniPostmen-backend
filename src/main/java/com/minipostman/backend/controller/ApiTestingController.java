package com.minipostman.backend.controller;

import com.minipostman.backend.dto.ApiRequestDTO;
import com.minipostman.backend.dto.ApiResponseDTO;
import com.minipostman.backend.service.ApiTestingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ApiTestingController {

    private final ApiTestingService apiTestingService;

    public ApiTestingController(ApiTestingService apiTestingService) {
        this.apiTestingService = apiTestingService;
    }

    @PostMapping("/test")
    public ApiResponseDTO testApi(@RequestBody ApiRequestDTO requestDTO) {
        return apiTestingService.sendRequest(requestDTO);
    }
}