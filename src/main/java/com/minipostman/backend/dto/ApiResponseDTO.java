package com.minipostman.backend.dto;

import java.util.Map;

public class ApiResponseDTO {
    private int status;
    private Map<String,String> headers;
    private String body;
    private long responseTime;

    public int getStatus(){
        return status;
    }
    public void setStatus(int status){
        this.status = status;
    }

   public Map<String, String> getHeaders() {
        return headers;
    }

   public void setHeaders(Map<String,String> headers){
    this.headers = headers;
   }

   public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public long getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(long responseTime) {
        this.responseTime = responseTime;
    }


}
