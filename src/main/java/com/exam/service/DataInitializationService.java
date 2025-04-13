package com.exam.service;

import com.exam.model.Question;
import com.exam.model.Test;
import com.exam.repository.QuestionRepository;
import com.exam.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class DataInitializationService {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostConstruct
    @Transactional
    public void initializeData() {
        // Check if test exists
        Optional<Test> existingTest = testRepository.findById(2L);
        Test test;
        
        if (existingTest.isEmpty()) {
            test = new Test();
            test.setId(2L);
            test.setTitle("Science Quiz");
            test.setDuration(45);
            test = testRepository.save(test);
            System.out.println("Created new test with ID: " + test.getId());
        } else {
            test = existingTest.get();
            System.out.println("Found existing test with ID: " + test.getId());
        }

        // Check if question exists
        Optional<Question> existingQuestion = questionRepository.findById(2L);
        if (existingQuestion.isEmpty()) {
            Question question = new Question();
            question.setId(2L);
            question.setText("What is H2O?");
            question.setTest(test);
            question = questionRepository.save(question);
            System.out.println("Created new question with ID: " + question.getId());
        } else {
            System.out.println("Found existing question with ID: 2");
        }
    }
}