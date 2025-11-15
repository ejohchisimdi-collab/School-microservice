package com.chisimdi.enrollment_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;
@Entity
public class Enrollment {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int id;
        @NotNull(message = "courseId cannot be empty")
        @Positive(message = "course Id must be positive")
        private int courseId;
        @NotNull(message = "course Name cannot be null")
        @Pattern(regexp = "^[A-Za-z]+(\\d)*$",message = "course name must be valid eg science101")
        private String courseName;
        @NotNull(message = "studentId cannot be empty ")
        @Positive(message = "student Id must be positive ")
        private int studentId;
        private int teacherId;


        public void setCourseId(int courseId) {
            this.courseId = courseId;
        }

        public int getCourseId() {
            return courseId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCourseName() {
            return courseName;
        }

        public void setCourseName(String courseName) {
            this.courseName = courseName;
        }

        public int getStudentId() {
            return studentId;
        }


    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }
}



