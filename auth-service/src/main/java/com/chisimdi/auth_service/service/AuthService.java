package com.chisimdi.auth_service.service;

import com.chisimdi.auth_service.Exceptions.InvalidCredentialsException;
import com.chisimdi.auth_service.Exceptions.UserAlreadyExistsException;
import com.chisimdi.auth_service.utils.JwUtil;
import com.chisimdi.auth_service.models.StudentDTO;
import com.chisimdi.auth_service.models.TeacherDTO;
import com.chisimdi.auth_service.models.User;
import com.chisimdi.auth_service.repository.UserRepository;
import com.chisimdi.auth_service.service.feignClients.StudentService;
import com.chisimdi.auth_service.service.feignClients.TeacherService;
import com.chisimdi.auth_service.utils.LoginResponse;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthService.class);
    private UserRepository userRepository;
    private JwUtil jwUtil;
    private TeacherService teacherService;
    private StudentService studentService;
    private BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,JwUtil jwUtil,TeacherService teacherService,StudentService studentService,BCryptPasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.jwUtil=jwUtil;
        this.teacherService=teacherService;
        this.studentService=studentService;
        this.passwordEncoder=passwordEncoder;
    }
@Transactional
    public User Register(User user){
        log.info("Registering user  with user name {}",user.getUserName());
        TeacherDTO teacherDTO=new TeacherDTO();
        StudentDTO studentDTO=new StudentDTO();
        String HashedPassword=passwordEncoder.encode(user.getPassword());
    if( userRepository.existsByUserName(user.getUserName())){
        log.warn("user with user name {} already exists",user.getUserName());
        throw  new UserAlreadyExistsException("user Already exists");}
        user.setPassword(HashedPassword);
        userRepository.save(user);




        if(user.getRole().trim().equalsIgnoreCase("Teacher")){
log.info("Saving user to teacher repository");
        teacherDTO.setAge(user.getAge());
        teacherDTO.setContactInfo(user.getContactInfo());
        teacherDTO.setName(user.getName());
        teacherDTO.setId(user.getId());
        teacherService.addTeacher(teacherDTO);
        return user;
        }
        else if(user.getRole().trim().equalsIgnoreCase("Student")){
            log.info("saving user to student repository");
            studentDTO.setAge(user.getAge());
            studentDTO.setId(user.getId());
            studentDTO.setContactInfo(user.getContactInfo());
            studentDTO.setName(user.getName());
            studentService.addStudent(studentDTO);
            return user;
        }
        return user;


    }
    @Transactional
    public LoginResponse Login(String username, String password){
        log.info("Logging in to account with username {}",username);
       User user= userRepository.findByUserName(username);
        if(!passwordEncoder.matches(password,user.getPassword())){
            log.info("invalid password");
            throw new InvalidCredentialsException("password is invalid");
        }

      String token=  jwUtil.generateToken(user.getUserName(),user.getId(),user.getRole());
        LoginResponse loginResponse=new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setRole(jwUtil.extractRoles(token));
        loginResponse.setUsername(jwUtil.extractUsername(token));
        loginResponse.setUserId(jwUtil.extractUserId(token));
        return loginResponse;
    }
}
