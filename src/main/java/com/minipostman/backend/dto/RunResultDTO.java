
package com.minipostman.backend.dto;

public class RunResultDTO {

    private String url;
    private String method;
    private int expectedStatus;
    private int actualStatus;
    private boolean passed;

    public RunResultDTO(String url, String method, int expectedStatus, int actualStatus, boolean passed) {
        this.url = url;
        this.method = method;
        this.expectedStatus = expectedStatus;
        this.actualStatus = actualStatus;
        this.passed = passed;
    }

    public String getUrl() { return url; }
    public String getMethod() { return method; }
    public int getExpectedStatus() { return expectedStatus; }
    public int getActualStatus() { return actualStatus; }
    public boolean isPassed() { return passed; }
}