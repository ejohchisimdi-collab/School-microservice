package com.chisimdi.enrollment_service.exception;

import java.time.LocalDateTime;

public class ApiError {
    private int StatusCode;
    private String message;
    private LocalDateTime localDateTime;

    public ApiError(int StatusCode,String message){
        this.StatusCode=StatusCode;
        this.message=message;
        this.localDateTime=LocalDateTime.now();
    }

    public String getMessage() {
        return message;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public void setStatusCode(int statusCode) {
        StatusCode = statusCode;
    }

}
