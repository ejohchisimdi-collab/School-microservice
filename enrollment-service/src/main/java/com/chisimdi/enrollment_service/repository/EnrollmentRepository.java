package com.chisimdi.enrollment_service.repository;
import com.chisimdi.enrollment_service.model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment,Integer> {
public Enrollment findByCourseIdAndStudentId(int courseId,int studentId);
public List<Enrollment> findByStudentId(int studentId);
public List<Enrollment> findByCourseId(int courseId);
}
