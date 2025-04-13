package com.exam.service;

import com.exam.model.StudentAnswer;
import com.exam.repository.StudentAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AutoSaveService {
    
    @Autowired
    private StudentAnswerRepository studentAnswerRepository;

    @Transactional
    public List<StudentAnswer> saveOrUpdateAnswers(Long studentId, Long testId, List<StudentAnswer> answers) {
        if (studentId == null || testId == null) {
            throw new IllegalArgumentException("Student ID and Test ID are required");
        }
        if (answers == null || answers.isEmpty()) {
            throw new IllegalArgumentException("Answers list cannot be empty");
        }

        for (StudentAnswer answer : answers) {
            // Set metadata
            answer.setStudentId(studentId);
            answer.setTestId(testId);
            answer.setTimestamp(LocalDateTime.now());

            // Find existing answer
            Optional<StudentAnswer> existingAnswer = studentAnswerRepository
                .findByStudentIdAndTestIdAndQuestionId(studentId, testId, answer.getQuestionId());

            if (existingAnswer.isPresent()) {
                // Update existing answer
                StudentAnswer current = existingAnswer.get();
                current.setResponse(answer.getResponse());
                current.setStatus(answer.getStatus());
                current.setTimestamp(LocalDateTime.now());
                studentAnswerRepository.save(current);
            } else {
                // Save new answer
                studentAnswerRepository.save(answer);
            }
        }
        
        return studentAnswerRepository.findByStudentIdAndTestId(studentId, testId);
    }
}