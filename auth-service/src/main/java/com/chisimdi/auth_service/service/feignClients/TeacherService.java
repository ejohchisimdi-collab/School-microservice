package com.chisimdi.auth_service.service.feignClients;

import com.chisimdi.auth_service.models.TeacherDTO;
import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "teacher-service")
public interface TeacherService {

    @PostMapping("/teachers")
    public TeacherDTO addTeacher(@RequestBody  TeacherDTO teacher);
}
