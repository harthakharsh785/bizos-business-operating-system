package com.bizos.exception;

import java.time.LocalDateTime;
import java.util.Map;

public class ErrorResponse {
    private int status;
    private String message;
    private LocalDateTime timestamp;
    private Map<String, String> fieldErrors;

    public ErrorResponse() {
    }

    public ErrorResponse(int status, String message, LocalDateTime timestamp, Map<String, String> fieldErrors) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
        this.fieldErrors = fieldErrors;
    }

    public int getStatus() { return status; }
    public void setStatus(int status) { this.status = status; }

    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }

    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }

    public Map<String, String> getFieldErrors() { return fieldErrors; }
    public void setFieldErrors(Map<String, String> fieldErrors) { this.fieldErrors = fieldErrors; }

    public static ErrorResponseBuilder builder() {
        return new ErrorResponseBuilder();
    }

    public static class ErrorResponseBuilder {
        private int status;
        private String message;
        private LocalDateTime timestamp;
        private Map<String, String> fieldErrors;

        public ErrorResponseBuilder status(int status) { this.status = status; return this; }
        public ErrorResponseBuilder message(String message) { this.message = message; return this; }
        public ErrorResponseBuilder timestamp(LocalDateTime timestamp) { this.timestamp = timestamp; return this; }
        public ErrorResponseBuilder fieldErrors(Map<String, String> fieldErrors) { this.fieldErrors = fieldErrors; return this; }

        public ErrorResponse build() {
            return new ErrorResponse(status, message, timestamp, fieldErrors);
        }
    }
}
