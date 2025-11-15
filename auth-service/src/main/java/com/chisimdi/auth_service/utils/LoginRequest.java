package com.chisimdi.auth_service.utils;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class LoginRequest {
    @NotNull(message = "username cannot be empty")
    @Size(min = 5, max = 10,message = "Username cannot be less than 5 characters or more than 10 characters")
    public String userName;
    @NotNull(message = "password cannot be empty")
    public String password;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

}
