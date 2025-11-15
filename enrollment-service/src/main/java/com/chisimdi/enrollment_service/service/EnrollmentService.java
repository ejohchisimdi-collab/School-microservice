package com.chisimdi.enrollment_service.service;

import com.chisimdi.enrollment_service.exception.CapacityException;
import com.chisimdi.enrollment_service.exception.ResourceNotFoundException;
import com.chisimdi.enrollment_service.model.CourseDTO;
import com.chisimdi.enrollment_service.model.Enrollment;
import com.chisimdi.enrollment_service.model.StudentDTO;
import com.chisimdi.enrollment_service.repository.EnrollmentRepository;
import com.chisimdi.enrollment_service.service.feignClients.CourseService;
import com.chisimdi.enrollment_service.service.feignClients.StudentService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService {
    private CourseService courseService;
    private StudentService studentService;
    private EnrollmentRepository enrollmentRepository;
    private final static Logger log= LoggerFactory.getLogger(EnrollmentService.class);

    public EnrollmentService(CourseService courseService, StudentService studentService,EnrollmentRepository enrollmentRepository){
        this.courseService=courseService;
        this.studentService=studentService;
        this.enrollmentRepository=enrollmentRepository;
    }
@Transactional
    public Enrollment enrollForACourse(int studentId,int courseId){
        log.info("Enrolling student with Id {} to course with ID {}",studentId,courseId);
        StudentDTO studentDTO=studentService.findSpecificStudent(studentId);
        log.info("Searching for student with Id {}",studentId);
        CourseDTO courseDTO= courseService.getSpecificCourse(courseId);
        log.info("Searching for course with ID {}",courseId);
        Enrollment enrollment=new Enrollment();

            if(courseDTO.getCourseCapacity()==0) {
                log.warn("No more space remaining in course with id {}",courseId);
                throw  new CapacityException("Course with Id "+courseId+" is full");
            }
            log.debug("Enrolling for course");
                courseDTO.setCourseCapacity(courseDTO.getCourseCapacity()-1);
                courseService.addCourses(courseDTO);
                enrollment.setCourseId(courseDTO.getId());
                enrollment.setCourseName(courseDTO.getName());
                enrollment.setStudentId(studentDTO.getId());
                enrollment.setTeacherId(courseDTO.getTeacherId());
                enrollmentRepository.save(enrollment);
                log.info("enrollment successful");
                return enrollment;




    }
    @Transactional
    public Enrollment transferToACourse(int studentId,int oldCourseId,int newCourseId){
        log.info("Transferring Student with Id {} to course with id {} from course with Id {}  ",studentId,newCourseId,oldCourseId);
        Enrollment enrollment= enrollmentRepository.findByCourseIdAndStudentId(oldCourseId,studentId);
        log.info("Searching for enrollment with student Id {} and course Id {}",studentId,oldCourseId);
        if(enrollment==null){
            log.warn("Enrollment with student id {} and course Id {} not found",studentId,oldCourseId);
            throw  new ResourceNotFoundException("Enrollment with student Id "+studentId+ " and course Id "+ oldCourseId+" not found");
        }
        CourseDTO newCourseDTO =courseService.getSpecificCourse(newCourseId);
        log.info("searching for course with id {}",newCourseId);
        CourseDTO oldCourseDTO=courseService.getSpecificCourse(oldCourseId);
        log.info("Looking for course with ID {}",oldCourseId);


            if(newCourseDTO.getCourseCapacity()==0) {
                log.warn("Course with Id {} is full",newCourseId);
                throw new CapacityException("Course with Id "+newCourseId+" is full");
            }
            log.debug("Transferring to course");
                newCourseDTO.setCourseCapacity(newCourseDTO.getCourseCapacity()-1);
                oldCourseDTO.setCourseCapacity(oldCourseDTO.getCourseCapacity()+1);
                courseService.addCourses(newCourseDTO);
                courseService.addCourses(oldCourseDTO);

                Enrollment newEnrollment=new Enrollment();
                newEnrollment.setStudentId(enrollment.getStudentId());
                newEnrollment.setCourseId(newCourseDTO.getId());
                newEnrollment.setCourseName(newCourseDTO.getName());
                newEnrollment.setTeacherId(oldCourseDTO.getTeacherId());
                enrollmentRepository.delete(enrollment);
                enrollmentRepository.save(newEnrollment);
                log.info("Transfer completed successfully");
                return newEnrollment;


   }
    public List<Enrollment>viewAllByStudent(int studentId){
log.info("searching for enrollment by Student id {}",studentId);
        List<Enrollment> enrollment= enrollmentRepository.findByStudentId(studentId);
        if(enrollment.isEmpty()){
            log.warn("enrollments with student  Id {} not found",studentId);
            throw new ResourceNotFoundException("Enrollment with Student Id "+ studentId+ " not found");

        }
        log.info("found enrollments");
        return enrollment;
    }
    public List<Enrollment>viewAllByCourses(int courseId){
        log.info("searching for enrollment by course id {}",courseId);
        List<Enrollment> enrollment=enrollmentRepository.findByCourseId(courseId);
        if(enrollment.isEmpty()){
            log.warn("enrollments with course  Id {} not found",courseId);
            throw new ResourceNotFoundException("Enrollment with Course Id "+ courseId+ " not found");

        }
        log.info("found enrollments");
        return enrollment;
    }
    public Enrollment findByCourseAndStudent(int courseId,int studentId){
        log.info("searching for enrollment by course id {} and studentId {}",courseId,studentId);
        Enrollment enrollment= enrollmentRepository.findByCourseIdAndStudentId(courseId,studentId);
        if(enrollment==null){
            log.warn("enrollments with course  Id {} and student Id {} not found",courseId,studentId);
            throw new ResourceNotFoundException("Enrollment with StudentId "+studentId+" and CourseId "+courseId+ " not found");
        }
        log.info("found enrollments");
        return enrollment;
    }
}
