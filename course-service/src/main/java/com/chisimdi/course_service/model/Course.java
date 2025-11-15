package com.chisimdi.course_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    @Pattern(regexp = "^[A-Za-z]+(\\d)*$",message = "course name must be valid eg science101")
    @NotNull(message = "name cannot be empty")
    private String name;
    @NotNull(message = "course description cannot be empty")
    @Size(min = 5, max=50,message = "Must be a minimum of 5 characters and not more than 50 characters")
    private String description;

    private String syllabus;
    private int teacherId;
    @NotNull(message = "course capacity cannot be empty")
    @Positive(message = "course capacity must be positive")
    private int courseCapacity;
    LocalDate courseCreationDate=LocalDate.now();

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getTeacherId() {
        return teacherId;
    }

    public String getDescription() {
        return description;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    public int getCourseCapacity() {
        return courseCapacity;
    }

    public void setCourseCapacity(int courseCapacity) {
        this.courseCapacity = courseCapacity;
    }
}
