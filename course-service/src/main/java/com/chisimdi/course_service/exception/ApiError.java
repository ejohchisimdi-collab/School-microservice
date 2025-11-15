package com.chisimdi.course_service.exception;

import java.time.LocalDateTime;

public class ApiError {
    private int  status;
    private String message;
    private LocalDateTime localDateTime;

    public ApiError(int status, String message){
        this.status=status;
        this.message=message;
        this.localDateTime=LocalDateTime.now();
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
