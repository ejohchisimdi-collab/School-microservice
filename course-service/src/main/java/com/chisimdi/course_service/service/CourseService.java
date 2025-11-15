package com.chisimdi.course_service.service;

import com.chisimdi.course_service.exception.ResourceNotFoundException;
import com.chisimdi.course_service.service.feignClient.TeacherService;
import com.chisimdi.course_service.model.Course;
import com.chisimdi.course_service.model.TeacherDTO;
import com.chisimdi.course_service.repository.CourseRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {
    private TeacherService teacherService;
    private CourseRepository courseRepository;
private final static Logger log= LoggerFactory.getLogger(CourseService.class);
    public CourseService(TeacherService teacherService,CourseRepository courseRepository){
        this.courseRepository=courseRepository;
        this.teacherService=teacherService;
    }
@Transactional
    public Course createNewCourse(Course course){
        log.info("saving course with id {}",course.getId());
        return courseRepository.save(course);
    }
    @Transactional
    public Course assignTeacherToCourse(int courseId,int teacherId){
        log.info("assigning teacher with id {} to course with id {}",teacherId,courseId);
        TeacherDTO teacherDTO= teacherService.findSpecificTeacher(teacherId);
        log.info("searching for teacher with id {}",teacherId);
        Course course=courseRepository.findById(courseId).orElse(null);
        log.info("Searching for course with Id {} ",courseId);
        if(course==null){
            log.warn("Course with ID {} not found",courseId );
            throw new ResourceNotFoundException("Course with Id "+courseId+ " not found");
        }
log.debug("Assigning teacher to course");
            course.setTeacherId(teacherDTO.getId());
            courseRepository.save(course);
            log.info("successfully assigned teacher to course");
            return course;



    }
    public Course findSPecificCourse(int courseId){
        log.info("Looking for course with Id {} ",courseId);
        Course course=  courseRepository.findById(courseId).orElse(null);
        if(course==null){
            log.warn("could not find course with Id: {}",courseId);
            throw new ResourceNotFoundException("Course with Id "+courseId+ " not found");

        }
        log.info("Successfully found course");

        return course;
    }
    public List<Course>findAllCourses(){
        log.info("Searching for all courses");
        return courseRepository.findAll();
    }
    public String deleteSpecificCourse(int courseId){
        log.info("Locating course with Id: {}",courseId);
        Course course=courseRepository.findById(courseId).orElse(null);
        if(course==null){
            log.warn("Could not find course with Id {}",courseId);
            throw new ResourceNotFoundException("course with Id "+ courseId+" not found");
        }
        log.debug("Deleting course");
        courseRepository.deleteById(courseId);
        log.info("Successfully deleted course");
        return "course deleted";
    }
    @Transactional
    public Course pasteCourseSyllabus(int courseId, String syllabus){
       Course course= courseRepository.findById(courseId).orElse(null);
log.info("Finding course with Id {}",courseId);
       if(course==null) {
           log.warn("Couldn't Locate course with ID {}",courseId);
           throw new ResourceNotFoundException("Course with Id "+courseId+ " not found");

       }
       log.debug("Setting Syllabus to course");
           course.setSyllabus(syllabus);
           courseRepository.save(course);
           log.info("syllabus saved successfully");
           return course;



       }
    }





