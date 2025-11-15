package com.chisimdi.auth_service.models;

import jakarta.validation.constraints.*;

public class StudentDTO {
   private int id;
    @Pattern(regexp = "^[A-Za-z]+$" ,message = "Must be a name")
    @Size(min = 3)
    @NotNull(message = "Name cannot be empty")
    private String name;
    @NotNull(message = "name cannot be empty")
    @Min(value = 5,message = "Age cannot be less than 5")
    private int age;
    @NotNull(message = "email cannot be empty")
    @Email
    private String contactInfo;

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }


}
