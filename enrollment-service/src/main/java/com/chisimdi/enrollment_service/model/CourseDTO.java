package com.chisimdi.enrollment_service.model;

import java.time.LocalDate;

public class CourseDTO {
    int id;
    private String name;
    private String description;
    private String syllabus;
    private int teacherId;
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
