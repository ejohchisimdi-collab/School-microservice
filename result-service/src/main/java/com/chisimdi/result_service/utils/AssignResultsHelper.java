package com.chisimdi.result_service.utils;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class AssignResultsHelper {
    @NotNull(message = "semester cannot be empty")
            @Pattern(regexp = "^Fall|Spring",message = "semester can only be Fall or Spring")
    String semester;
    @NotNull(message = "course Id cannot be empty")
            @Positive(message = "course Id can only be positive")
    int courseId;
    @NotNull(message = "student Id cannot be empty")
            @Positive(message = "Student Id can only be positive")
    int studentId;
    @NotNull(message = "Grade cannot be empty")
            @Positive(message = "Grade must be positive")
    int grade;

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getSemester() {
        return semester;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
