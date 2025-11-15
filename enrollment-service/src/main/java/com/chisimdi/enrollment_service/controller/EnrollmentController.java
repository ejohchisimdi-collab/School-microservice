package com.chisimdi.enrollment_service.controller;

import com.chisimdi.enrollment_service.model.Enrollment;
import com.chisimdi.enrollment_service.service.EnrollmentService;
import com.chisimdi.enrollment_service.utils.EnrollHelper;
import com.chisimdi.enrollment_service.utils.TransferHelper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Validated
@RestController
public class EnrollmentController {
    private EnrollmentService enrollmentService;
    private static final Logger log=LoggerFactory.getLogger(EnrollmentController.class);
    public EnrollmentController(EnrollmentService enrollmentService){
        this.enrollmentService=enrollmentService;
    }
@PreAuthorize("hasAuthority('Student') and principal.userId == #enrollHelper.studentId")
    @PostMapping("/enrollment")
    public Enrollment enroll(@Valid @RequestBody @P("enrollHelper") EnrollHelper enrollHelper){
        log.info("Post /enrollment student Id {}, courseId {}",enrollHelper.getStudentId(),enrollHelper.getCourseId());
        return enrollmentService.enrollForACourse(enrollHelper.getStudentId(),
                enrollHelper.getCourseId());

    }
    @PreAuthorize("hasAuthority('Student') and principal.userId == #transferHelper.studentId")
    @PostMapping ("/transfer")
    public Enrollment transfer(@Valid @RequestBody TransferHelper transferHelper){
        log.info("Post /transfer student ID {},Old Course Id {},New Course Id {}",
                transferHelper.getStudentId(),transferHelper.getOldCourseId(),transferHelper.getNewCourseId());
        return enrollmentService.transferToACourse(transferHelper.getStudentId(),
                transferHelper.getOldCourseId(), transferHelper.getNewCourseId());

    }
    @PreAuthorize("hasAuthority('Admin') or (hasAuthority('Student') and principal.userId == #studentId)")
    @GetMapping("/enrollment/student/{studentId}")
    public List<Enrollment>viewEnrollmentByStudent(@PathVariable("studentId")
                                                       @Positive(message = "student Id must be positive") int studentId){
        log.info("Get /enrollment/student/{studentId}, student Id {}",studentId);
        return enrollmentService.viewAllByStudent( studentId);
    }
    @PreAuthorize("hasAuthority('Admin') or (hasAuthority('Teacher') and @courseHelper.validateTeacher(#courseId,principal.userId))")
    @GetMapping("/enrollment/course/{courseId}")
    public List<Enrollment>viewEnrollmentByCourse(@PathVariable("courseId")
                                                      @Positive(message = "student Id must be positive") int courseId){
        log.info("Get /enrollment/course/{courseId},course Id {}",courseId);
        return enrollmentService.viewAllByCourses(courseId);
    }
    @GetMapping("/enrollment/course/{courseId}/student/{studentId}")
    public Enrollment viewEnrollmentBySTudentAndCourse(@PathVariable("courseId")
                                                       @Positive(message = "must be positive") int courseId,
                                                       @PathVariable("studentId")
                                                               @Positive(message = "must be positive")
                                                       int studentId){
        log.info("/enrollment/course/{courseId}/student/{studentId}, course ID {}, student Id {}",courseId,studentId);
        return enrollmentService.findByCourseAndStudent(courseId,studentId);
    }

}
