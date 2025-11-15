package com.chisimdi.auth_service.Exceptions;

import java.time.LocalDateTime;

public class ApiError {
   private String message;
   private int status;
    private LocalDateTime localDateTime;

    public ApiError(String message,int status){
        this.message=message;
        this.status=status;
        this.localDateTime=LocalDateTime.now();
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
