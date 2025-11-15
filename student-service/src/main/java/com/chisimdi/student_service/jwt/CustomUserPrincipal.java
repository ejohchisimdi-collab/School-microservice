package com.chisimdi.student_service.jwt;

public class CustomUserPrincipal  {
    int userId;
    String role;
    String username;

    public CustomUserPrincipal(int userId, String role, String username){
        this.role=role;
        this.username=username;
        this.userId=userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public int getUserId() {
        return userId;
    }

}
