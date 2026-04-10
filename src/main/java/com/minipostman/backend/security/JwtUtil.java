package com.minipostman.backend.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.Date;


import javax.crypto.SecretKey;

@Component
public class JwtUtil {

    private final String SECRET_KEY = "mysecretkeymysecretkeymysecretkey";

    private final SecretKey key = Keys.hmacShaKeyFor(
            SECRET_KEY.getBytes(StandardCharsets.UTF_8)
    );

    public String generateToken(String email){

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(key)
                .compact();
    }
    public String extractEmail(String token) {
    return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
}
    
}