package com.exam.config;

import com.exam.model.Question;
import com.exam.model.Student;
import com.exam.model.Test;
import com.exam.repository.QuestionRepository;
import com.exam.repository.StudentRepository;
import com.exam.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private TestRepository testRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private StudentRepository studentRepository;

    @Override
    @Transactional
    public void run(String... args) {
        System.out.println("Initializing test data...");

        // Create test
        Test test = new Test();
        test.setTitle("Science Quiz");
        test.setDuration(45);
        test = testRepository.save(test);
        System.out.println("Created test with ID: " + test.getId());

        // Create multiple questions
        Question question1 = new Question();
        question1.setText("What is H2O?");
        question1.setTest(test);
        questionRepository.save(question1);

        Question question2 = new Question();
        question2.setText("What is the chemical symbol for Gold?");
        question2.setTest(test);
        questionRepository.save(question2);

        // Create student
        Student student = new Student();
        student.setName("Ravi");
        student.setEmail("ravi@example.com");
        studentRepository.save(student);

        System.out.println("Test data initialization completed");
    }
}