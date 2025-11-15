package com.chisimdi.auth_service.models;

import jakarta.validation.constraints.*;

public class TeacherDTO {
    int id;
    @NotNull(message = "name cannot be empty")
    @Size(min = 3 ,message = "name must have more than three characters")
    @Pattern(regexp = "^[A-Za-z]+$",message = "enter a valid name")
    private String name;
    @Email
    @NotNull(message = "email cannot be empty")
    private String contactInfo;
    @Min(value = 20,message = "Must be at least 20 to be a teacher")
    @Max(value = 70,message = "cannot be a teacher past 70")
    @NotNull(message = "age cannot be empty")
    private int age;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAge() {
        return age;
    }
}
