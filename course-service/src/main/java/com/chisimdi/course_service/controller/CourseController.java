package com.chisimdi.course_service.controller;

import com.chisimdi.course_service.model.Course;
import com.chisimdi.course_service.service.CourseService;
import com.chisimdi.course_service.utils.SyllabusHelper;
import com.chisimdi.course_service.utils.TeacherAssignmentHelper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Validated
@RestController
public class CourseController {
    private static final Logger log= LoggerFactory.getLogger(CourseController.class);

    private CourseService courseService;

    public CourseController (CourseService courseService){
        this.courseService=courseService;
    }
    @PostMapping("/courses")
    public Course addCourses(@RequestBody @Valid Course course){
       log.info("Get /courses ");
        return courseService.createNewCourse(course );
    }
    @GetMapping("/courses")
    public List<Course> getAllCourses(){
        log.info("Get /courses");
        return courseService.findAllCourses();
    }
    @GetMapping("/courses/{courseId}")
    public Course getSpecificCourse(@PathVariable ("courseId")
                                        @Positive(message = "course Id must be positive") int courseId){
        log.info("Get /courses/{courseId}, courseId {}",courseId);
        return courseService.findSPecificCourse(courseId);
    }
    @DeleteMapping("/courses/{courseID}")
    public String deleteSpecificCourse(@PathVariable("courseId")
                                           @Positive(message = "courseId must be positive") int courseId){
        log.info("Delete /courses/{courseId}, courseId {}",courseId);
        return courseService.deleteSpecificCourse(courseId);
    }
@PostMapping("/courses/teachers")
    public Course assignTeacher(@Valid @RequestBody TeacherAssignmentHelper assignmentHelper){
        log.info("Post /courses/teachers, courseId: {}, teacherId: {} ",assignmentHelper.getCourseId(),assignmentHelper.getTeacherId());
        return courseService.assignTeacherToCourse(assignmentHelper.getCourseId(),assignmentHelper.getTeacherId());
}
@PostMapping("/courses/syllabus")
    public Course pasteSyllabus(@Valid @RequestBody SyllabusHelper syllabusHelper){
        log.info("Post /courses/syllabus, courseId: {}, syllabus: {}",syllabusHelper.getCourseId(),syllabusHelper.getSyllabus());
        return courseService.pasteCourseSyllabus(syllabusHelper.getCourseId(),syllabusHelper.getSyllabus());
}
}
