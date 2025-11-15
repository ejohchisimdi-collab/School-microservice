package com.chisimdi.enrollment_service.utils;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class TransferHelper {
    @NotNull(message = "student Id cannot be empty")
            @Positive(message = "student Id must be positive")
    int studentId;
    @Positive(message = "course Id must be positive")
    @NotNull(message = "course Id cannot be empty")
    int oldCourseId;
    @NotNull(message = "new course Id cannot be empty")
            @Positive(message = "new course Id must be positive")
    int newCourseId;

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getStudentId() {
        return studentId;
    }

    public int getNewCourseId() {
        return newCourseId;
    }

    public int getOldCourseId() {
        return oldCourseId;
    }

    public void setNewCourseId(int newCourseId) {
        this.newCourseId = newCourseId;
    }

    public void setOldCourseId(int oldCourseId) {
        this.oldCourseId = oldCourseId;
    }

}
