package com.chisimdi.enrollment_service.service.feignClients;

import com.chisimdi.enrollment_service.model.StudentDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "student-service")
public interface StudentService {

    @GetMapping("/students/{studentId}")
    public StudentDTO findSpecificStudent(@PathVariable("studentId")int studentId);
}
