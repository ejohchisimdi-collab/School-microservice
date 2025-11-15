package com.chisimdi.course_service.service.feignClient;

import com.chisimdi.course_service.model.TeacherDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "teacher-service")
public interface TeacherService {

    @GetMapping("/teachers/{teacherId}")
    public TeacherDTO findSpecificTeacher(@PathVariable("teacherId")int teacherId);
}
