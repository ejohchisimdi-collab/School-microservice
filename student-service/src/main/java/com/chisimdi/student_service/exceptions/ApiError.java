package com.chisimdi.student_service.exceptions;

import java.time.LocalDateTime;

public class ApiError {
    private int responseStatus;
    private String message;
    private LocalDateTime localDateTime;

    public ApiError(int responseStatus, String message){
        this.responseStatus=responseStatus;
        this.message=message;
        this.localDateTime=LocalDateTime.now();
    }

    public int getResponseStatus() {
        return responseStatus;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }

}
