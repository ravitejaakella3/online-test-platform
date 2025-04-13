package com.exam.repository;

import com.exam.model.StudentAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, Long> {
    List<StudentAnswer> findByStudentIdAndTestId(Long studentId, Long testId);
    Optional<StudentAnswer> findByStudentIdAndTestIdAndQuestionId(Long studentId, Long testId, Long questionId);
}