package com.chisimdi.result_service.repository;

import com.chisimdi.result_service.model.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result,Integer> {

    List<Result>findByStudentId(int studentId);
    List<Result>findByCourseId(int courseId);
}
