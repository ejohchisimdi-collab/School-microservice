package com.chisimdi.enrollment_service.service.feignClients;

import com.chisimdi.enrollment_service.model.CourseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "course-service")
public interface CourseService {
    @GetMapping("/courses/{courseId}")
    public CourseDTO getSpecificCourse(@PathVariable("courseId")int courseId);

    @PostMapping("/courses")
    public CourseDTO addCourses(@RequestBody CourseDTO course);
}
