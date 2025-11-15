package com.chisimdi.student_service.controller;

import com.chisimdi.student_service.model.Student;
import com.chisimdi.student_service.service.StudentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Validated
public class StudentController {
    private final StudentService studentService;
private final Logger logger= LoggerFactory.getLogger(StudentController.class);
    public StudentController(StudentService studentService){
        this.studentService=studentService;
    }

    @PostMapping("/students")
    public Student addStudent(@RequestBody @Valid Student student)
    {logger.info("Post /Students, Student: {} ",student);
        return studentService.addStudent(student);
    }

    @GetMapping("/students/{studentId}")
    public Student findSpecificStudent( @PathVariable("studentId")
                                            @Positive(message ="studentId most be positive" ) int studentId){
        logger.info("Get /students/{studentId}, studentId: {}",studentId);
        return studentService.findStudentById(studentId);
    }
    @PreAuthorize("hasAuthority('Admin')")
    @GetMapping("/students")
    public List<Student>findAllStudents(){
        logger.info("Get /students");
        return studentService.findAllStudent();
    }
    @PreAuthorize("hasAuthority('Admin')")
    @DeleteMapping("/students/{studentId}")
    public String deleteStudents(@PathVariable("studentId")
                                     @Positive(message = "student Id most be positive ") int studentId){
        logger.info("Delete /students");
        return studentService.deleteSpecificStudent(studentId);

    }
}
