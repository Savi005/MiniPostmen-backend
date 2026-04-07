package com.minipostman.backend.dto;

public class SavedRequestDTO {
    private String name;
    private String url;
    private String method;
    private String headers;
    private String body;
    private Integer expectedStatus;
    private Long projectId; 
    
    
    public SavedRequestDTO() {}
    
    public SavedRequestDTO(String name, String url, String method, 
                           String headers, String body, Integer expectedStatus, Long projectId) {
        this.name = name;
        this.url = url;
        this.method = method;
        this.headers = headers;
        this.body = body;
        this.expectedStatus = expectedStatus;
        this.projectId = projectId;
    }
    
    // Getters and Setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    
    public String getMethod() { return method; }
    public void setMethod(String method) { this.method = method; }
    
    public String getHeaders() { return headers; }
    public void setHeaders(String headers) { this.headers = headers; }
    
    public String getBody() { return body; }
    public void setBody(String body) { this.body = body; }
    
    public Integer getExpectedStatus() { return expectedStatus; }
    public void setExpectedStatus(Integer expectedStatus) { this.expectedStatus = expectedStatus; }
    
    public Long getProjectId() { return projectId; }  // ← ADD THIS
    public void setProjectId(Long projectId) { this.projectId = projectId; }  // ← ADD THIS
}