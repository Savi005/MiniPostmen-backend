package com.minipostman.backend.service;

import com.minipostman.backend.dto.ApiRequestDTO;
import com.minipostman.backend.dto.ApiResponseDTO;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@Service
public class ApiTestingService {
    private final RestTemplate restTemplate = new RestTemplate();

    public ApiResponseDTO sendRequest(ApiRequestDTO requestDTO) {

    long startTime = System.currentTimeMillis();
    
    HttpHeaders headers = new HttpHeaders();

    if (requestDTO.getHeaders() != null) {
        // Loop through all headers in requestDTO and add them to the HttpHeaders object
        requestDTO.getHeaders().forEach(headers::set);
    }
    // Create an HTTP request entity containing the body and headers
    HttpEntity<String> entity = new HttpEntity<>(requestDTO.getBody(), headers);

    // Convert the HTTP method string from the request into a Spring HttpMethod enum
    HttpMethod method = HttpMethod.valueOf(requestDTO.getMethod().toUpperCase());

    ApiResponseDTO responseDTO = new ApiResponseDTO();

    try {

        ResponseEntity<String> response = restTemplate.exchange(
                requestDTO.getUrl(),
                method,
                entity,
                String.class
        );

        responseDTO.setStatus(response.getStatusCode().value());
        responseDTO.setBody(response.getBody());

        Map<String, String> responseHeaders = new HashMap<>();
        response.getHeaders().forEach((key, value) ->
                responseHeaders.put(key, String.join(",", value)));

        responseDTO.setHeaders(responseHeaders);

    } catch (Exception ex) {

        responseDTO.setStatus(500);
        responseDTO.setBody(ex.getMessage());
        responseDTO.setHeaders(new HashMap<>());

    }

    long endTime = System.currentTimeMillis();
    responseDTO.setResponseTime(endTime - startTime);

    return responseDTO;
}
}
