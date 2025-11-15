package com.chisimdi.teacher_service.service;

import com.chisimdi.teacher_service.exception.ResourceNotFoundException;
import com.chisimdi.teacher_service.model.Teacher;
import com.chisimdi.teacher_service.repository.TeacherRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TeacherService {

    private TeacherRepository teacherRepository;
private static final Logger log= LoggerFactory.getLogger(TeacherService.class);
    public TeacherService(TeacherRepository teacherRepository){

        this.teacherRepository=teacherRepository;
    }
@Transactional
    public Teacher addTeacher(Teacher teacher){
        log.info("Saving Teacher with Id: {}",teacher.getId());
        return teacherRepository.save(teacher);
    }
    public Teacher findTeacherById(int teacherId){
        log.info("searching fot teacher with ID: {}",teacherId);
       Teacher teacher=teacherRepository.findById(teacherId).orElse(null);
       if(teacher==null){
           log.warn("Teacher with Id: {} was not found",teacherId);
           throw new ResourceNotFoundException("Teacher with Id "+ teacherId+" not found");

       }
       log.info("Successfully found teacher with ID: {} ",teacherId);
        return teacher;

    }
    public List<Teacher> findAllTeachers(){
        log.info("searching for all teachers");
        return teacherRepository.findAll();
    }
    @Transactional
    public String deleteSpecificTeacher(int teacherId){
        log.info("Searching for Teachers with Id {} ",teacherId);
        Teacher teacher=teacherRepository.findById(teacherId).orElse(null);
        if (teacher==null){
            log.warn("Teacher with Id {} not found",teacherId);
            throw new ResourceNotFoundException("Teacher with Id "+ teacherId+" not found");

        }
        log.debug("Deleting teacher with Id: {}",teacherId);
        teacherRepository.deleteById(teacherId);
        log.info("Teacher with Id: {} successfully deleted ",teacherId);

        return "teacher deleted";
    }
}
