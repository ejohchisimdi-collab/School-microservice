package com.chisimdi.enrollment_service.utils;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class EnrollHelper {
    @NotNull(message = "student Id cannot be empty")
            @Positive(message = "student Id must be positive")
    int studentId;
    @Positive(message = "Course Id must be positive")
    @NotNull(message = "course Id cannot be empty")
    int courseId;

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public int getCourseId() {
        return courseId;
    }

}
