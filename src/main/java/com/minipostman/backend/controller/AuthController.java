package com.minipostman.backend.controller;

import com.minipostman.backend.dto.AuthRequestDTO;
import com.minipostman.backend.dto.AuthResponseDTO;
import com.minipostman.backend.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public AuthResponseDTO register(@RequestBody AuthRequestDTO dto) {
        String message = service.register(dto);
        return new AuthResponseDTO(message);
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody AuthRequestDTO dto) {
        String message = service.login(dto);
        return new AuthResponseDTO(message);
    }
}