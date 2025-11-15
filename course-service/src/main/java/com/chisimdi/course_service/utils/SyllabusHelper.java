package com.chisimdi.course_service.utils;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public class SyllabusHelper {
    @NotNull(message = "courseId cannot be empty")
            @Positive(message = "courseId must be positive")
    int courseId;
    @NotNull(message = "syllabus cannot be empty")
            @Pattern(regexp = "^(https?:)(\\/\\/)[A-za-z]+$")
    String syllabus;

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }

    public String getSyllabus() {
        return syllabus;
    }

    public int getCourseId() {
        return courseId;
    }

}
