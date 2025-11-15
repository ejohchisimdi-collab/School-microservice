package com.chisimdi.course_service.repository;

import com.chisimdi.course_service.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Integer> {

}
