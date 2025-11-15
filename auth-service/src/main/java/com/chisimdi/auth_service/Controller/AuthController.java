package com.chisimdi.auth_service.Controller;

import com.chisimdi.auth_service.models.User;
import com.chisimdi.auth_service.service.AuthService;
import com.chisimdi.auth_service.utils.LoginRequest;
import com.chisimdi.auth_service.utils.LoginResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestController
public class AuthController {
    private AuthService authService;

    public AuthController(AuthService authService){
        this.authService=authService;
    }
    @PostMapping("/register")
    public User register(@Valid @RequestBody User user){
        return authService.Register(user);
    }
    @PostMapping("/log-in")
    public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest){
        return authService.Login(loginRequest.userName,loginRequest.password);
    }

}
