package com.chisimdi.teacher_service.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class ApiError2 {
    String name;
    int status;
    LocalDateTime localDateTime;
    Map<String,String> errors=new HashMap<>();
    public ApiError2(String name,int status){
        this.name=name;
        this.status=status;
        this.localDateTime=LocalDateTime.now();
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setErrors(Map<String, String> errors) {
        this.errors = errors;
    }

    public Map<String, String> getErrors() {
        return errors;
    }

}
