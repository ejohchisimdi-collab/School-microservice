package com.chisimdi.result_service.service.feignClients;

import com.chisimdi.result_service.model.EnrollmentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "enrollment-service")
public interface EnrollmentService {

    @GetMapping("/enrollment/course/{courseId}/student/{studentId}")
    public EnrollmentDTO viewEnrollmentBySTudentAndCourse(@PathVariable("courseId")
                                                       int courseId,
                                                          @PathVariable("studentId")
                                                       int studentId);
}
