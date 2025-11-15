package com.chisimdi.student_service.service;

import com.chisimdi.student_service.exceptions.ResourceNotFoundException;
import com.chisimdi.student_service.model.Student;
import com.chisimdi.student_service.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import org.slf4j.*;

@Service
public class StudentService {
    private final static Logger log = LoggerFactory.getLogger(StudentService.class);
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository){

        this.studentRepository=studentRepository;

    }
    @Transactional
    public Student addStudent(Student student){
log.info("Creating new student with name: {}, age: {} and contact info {}",student.getName(),student.getAge(),student.getContactInfo());
        return studentRepository.save(student);
    }
    public Student findStudentById(int studentId){
        Student student= studentRepository.findById(studentId).orElse(null);
        log.info("Searching for student with Id: {}",studentId);
        if(student==null){
            log.warn("could not locate student with Id {}",studentId);
            throw new ResourceNotFoundException("Student with ID "+studentId+" was not found");

        }
        log.info("Successfully found student with id: {}",studentId);
            return student;

    }
    public List<Student>findAllStudent(){
        log.info("finding all students");
        return studentRepository.findAll();

    }
    @Transactional
    public String deleteSpecificStudent(int StudentId){
        log.info("Searching for student with ID: {}",StudentId);
         Student student=studentRepository.findById(StudentId).orElse(null);

         if(student==null){
             log.warn("Student with ID: {} not found",StudentId);
             throw  new ResourceNotFoundException("student with ID "+ StudentId+" not found");
         }
log.debug("deleting student with Id: {}",StudentId);
         studentRepository.deleteById(StudentId);
         log.info("Student with Id: {} deleted",StudentId);

         return "student deleted";
    }
    }

