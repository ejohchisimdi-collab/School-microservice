package com.chisimdi.auth_service.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
public class User {
    @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @NotNull(message = "user name cannot be empty")
    @Size(min=5,max=10, message = "username must be 5 to 10 letters long")
   private String userName;
    @NotNull(message = "name cannot be empty")
    @Pattern(regexp = "^[A-Za-z]+$",message = "name can only be made up of letters")
   private String name;
    @NotNull(message = "role cannot be empty")
    @Pattern(regexp = "^Student|Teacher|Admin$",message = "role must be either Student,Teacher or Admin")
   private String role;
    @NotNull(message = "password cannot be empty")
   private String password;
    @NotNull(message = "contact info cannot be empty")
    @Email
   private String contactInfo;
    @NotNull(message = "age cannot be empty")
   private int age;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getPassword() {
        return password;
    }

    public String getUserName() {
        return userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public int getAge() {
        return age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
