package com.minipostman.backend.service;

import com.minipostman.backend.dto.AuthRequestDTO;
import com.minipostman.backend.model.User;
import com.minipostman.backend.repository.UserRepository;
import com.minipostman.backend.security.JwtUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
    }

    public String register(AuthRequestDTO dto) {

        if (userRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new RuntimeException("User with this email already exists");
        }

        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());

        userRepository.save(user);

        return "User registered successfully";
    }

    public String login(AuthRequestDTO dto) {

        User user = userRepository.findByEmail(dto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!user.getPassword().equals(dto.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return token;
    }
}