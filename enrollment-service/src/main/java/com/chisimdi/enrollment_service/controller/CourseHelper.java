package com.chisimdi.enrollment_service.controller;

import com.chisimdi.enrollment_service.model.Enrollment;
import com.chisimdi.enrollment_service.repository.EnrollmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component("courseHelper")
public class CourseHelper {
    @Autowired
    EnrollmentRepository enrollmentRepository;

    public Boolean validateTeacher(int courseId, int userId) {
        Boolean valid=false;
        List<Enrollment> enrollments = enrollmentRepository.findByCourseId(courseId);
        for (Enrollment e : enrollments) {
            if (e.getTeacherId() == userId) {
                return true; // Found a match, return immediately
            }
        }
        return false;

    }
}
