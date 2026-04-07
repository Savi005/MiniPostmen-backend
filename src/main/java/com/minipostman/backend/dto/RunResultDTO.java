    package com.minipostman.backend.dto;

    public class RunResultDTO {

        private String url;
        private String method;
        private int expectedStatus;
        private int actualStatus;
        private boolean passed;
        private long responseTime;

        public RunResultDTO(String url, String method, int expectedStatus,
                            int actualStatus, boolean passed, long responseTime) {

            this.url = url;
            this.method = method;
            this.expectedStatus = expectedStatus;
            this.actualStatus = actualStatus;
            this.passed = passed;
            this.responseTime = responseTime;
        }

        public String getUrl() { return url; }
        public String getMethod() { return method; }
        public int getExpectedStatus() { return expectedStatus; }
        public int getActualStatus() { return actualStatus; }
        public boolean isPassed() { return passed; }
        public long getResponseTime() { return responseTime; }
    }