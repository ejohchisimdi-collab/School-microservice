package com.chisimdi.teacher_service.exception;

import java.time.LocalDateTime;

public class ApiError {
    private int responseCode;
    private String message;
    private LocalDateTime localDateTime;

    public ApiError(int responseCode, String message){
        this.responseCode=responseCode;
        this.message=message;
        this.localDateTime=LocalDateTime.now();
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(int responseCode) {
        this.responseCode = responseCode;
    }
}
