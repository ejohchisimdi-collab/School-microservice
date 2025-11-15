package com.chisimdi.teacher_service.controller;

import com.chisimdi.teacher_service.model.Teacher;
import com.chisimdi.teacher_service.service.TeacherService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Validated
@RestController
public class TeacherController {
    private TeacherService teacherService;
private final Logger log= LoggerFactory.getLogger(TeacherController.class);
    public TeacherController(TeacherService teacherService){
        this.teacherService=teacherService;
    }

    @PostMapping("/teachers")
    public Teacher addTeacher(@RequestBody @Valid Teacher teacher){
        log.info("Post /teachers");
        return teacherService.addTeacher(teacher);
    }
    @GetMapping("/teachers/{teacherId}")
    public Teacher findSpecificTeacher(@PathVariable("teacherId")
                                           @Positive(message = "Teacher Id must be positive") int teacherId){
        log.info("Get /teachers /{teacherID},teacherId: {}",teacherId);
        return teacherService.findTeacherById(teacherId);
    }
    @GetMapping("/teachers")
    public List<Teacher>findALlTeachers()
    {log.info("Get /teachers");
        return teacherService.findAllTeachers();
    }
    @DeleteMapping("/teachers/{teacherId}")
    public String deleteTeacherByID(@PathVariable("teacherId")@Positive(message ="Teacher Id must be positive" ) int teacherId){
        log.info("Delete /teachers/{teacherId}, teacherID:{}",teacherId);
        return teacherService.deleteSpecificTeacher(teacherId);
    }
}
