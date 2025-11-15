package com.chisimdi.result_service.service;

import com.chisimdi.result_service.exceptions.ResourceNotFoundException;
import com.chisimdi.result_service.model.EnrollmentDTO;
import com.chisimdi.result_service.model.Result;
import com.chisimdi.result_service.repository.ResultRepository;
import com.chisimdi.result_service.service.feignClients.EnrollmentService;
import jakarta.transaction.Transactional;
import org.hibernate.internal.EntityManagerMessageLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResultService {
    private EnrollmentService enrollmentService;
    private ResultRepository resultRepository;
    private final static Logger log= LoggerFactory.getLogger(ResultService.class);

    public ResultService(EnrollmentService enrollmentService, ResultRepository resultRepository) {
        this.enrollmentService = enrollmentService;
        this.resultRepository = resultRepository;
    }
@Transactional
    public Result assignResults(String semester, int courseId, int studentId, int grade) {
        log.info("Assigning results for semester:{},course Id{},student Id {},grades {}",
                semester,courseId,studentId,grade);

        EnrollmentDTO enrollmentDTO = enrollmentService.viewEnrollmentBySTudentAndCourse(courseId, studentId);
        Result result = new Result();
log.debug("Assigning results");
        result.setCourseId(enrollmentDTO.getCourseId());
        result.setCourseName(enrollmentDTO.getCourseName());
        result.setStudentId(enrollmentDTO.getStudentId());
        result.setSemester(semester);
        result.setGrade(grade);
        result.setTeacherId(enrollmentDTO.getTeacherId());
        resultRepository.save(result);
        log.info("Result saved successfully");
        return result;


    }
    public List<Result>findResultByStudent(int StudentId){
log.info("Searching for results with Student Id {}",StudentId);
        List<Result> result= resultRepository.findByStudentId(StudentId);
        if(result.isEmpty()){
            log.warn("No results found with Student Id {}",StudentId);
            throw  new ResourceNotFoundException("Results with student Id "+ StudentId+ " not found");
        }
        log.info("Results found successfully");
        return result;
    }
    public List<Result>findResultBYCourse(int courseId){
        log.info("Searching for results with Course Id {}",courseId);
        List<Result> results=resultRepository.findByCourseId(courseId);
        if(results.isEmpty()){
            log.warn("No results found with Course Id {}",courseId);
            throw new ResourceNotFoundException("Results with course Id "+ courseId+ " not found");

        }
        log.info("Results found successfully");
        return results;
    }

}
