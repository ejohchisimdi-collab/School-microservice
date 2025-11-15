package com.chisimdi.auth_service.Exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;

public class ApiError2 {
    private String message;
    private int status;
    private LocalDateTime localDateTime;
    HashMap<String,String>errors=new HashMap<>();

    public ApiError2(String message,int status){
        this.message=message;
        this.status=status;
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

    public void setErrors(HashMap<String, String> errors) {
        this.errors = errors;
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

    public HashMap<String, String> getErrors() {
        return errors;
    }

}
