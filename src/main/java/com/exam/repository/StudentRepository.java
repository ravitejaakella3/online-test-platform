package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.exam.model.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}