package com.chisimdi.result_service.controller;

import com.chisimdi.result_service.model.Result;
import com.chisimdi.result_service.service.ResultService;
import com.chisimdi.result_service.utils.AssignResultsHelper;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Validated
@RestController
public class ResultController {
private final static Logger log= LoggerFactory.getLogger(ResultController.class);
    private ResultService resultService;

    public ResultController(ResultService resultService){
        this.resultService=resultService;
    }

    @PostMapping("/result")
    public Result assignResult(@Valid @RequestBody AssignResultsHelper assignResultsHelper){
        log.info("Post /result semester: {}, course Id : {}, student Id : {}, grade: {}",
                assignResultsHelper.getSemester(),assignResultsHelper.getCourseId(),assignResultsHelper.getStudentId(),assignResultsHelper.getGrade());
        return  resultService.assignResults(assignResultsHelper.getSemester(), assignResultsHelper.getCourseId(),assignResultsHelper.getStudentId(),assignResultsHelper.getGrade());
    }

    @GetMapping("/result/student/{studentId}")
    public List<Result>getResultByStudent(@PathVariable("studentId")
                                              @Positive(message = "student Id must be positive") int studentId){
        log.info("Get /result/student/{studentId}, studentID: {}",studentId);
        return resultService.findResultByStudent(studentId);
    }
    @GetMapping("/result/course/{courseId}")
    public List<Result>getResultByCourse(@PathVariable("courseId")
                                             @Positive(message = "courseId must be positive") int courseId){
        log.info("/result/course/{courseId}, courseId : {}",courseId);
        return resultService.findResultBYCourse(courseId);
    }
}
