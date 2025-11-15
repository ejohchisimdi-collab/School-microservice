package com.chisimdi.auth_service.service.feignClients;

import com.chisimdi.auth_service.models.StudentDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("student-service")
public interface StudentService {

    @PostMapping("/students")
    public StudentDTO addStudent(@RequestBody  StudentDTO student);
}
