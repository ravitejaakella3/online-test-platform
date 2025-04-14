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
import java.util.Optional;

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
        // Check if student exists
        Optional<Student> existingStudent = studentRepository.findById(1L);
        if (existingStudent.isEmpty()) {
            Student student = new Student();
            student.setName("Ravi");
            student.setEmail("ravi@example.com");
            studentRepository.save(student);
            System.out.println("Created new student");
        }

        // Check if test exists
        Optional<Test> existingTest = testRepository.findById(2L);
        Test test;
        if (existingTest.isEmpty()) {
            test = new Test();
            test.setTitle("Science Quiz");
            test.setDuration(45);
            test = testRepository.save(test);
            System.out.println("Created new test");

            // Create questions only if test is new
            Question question1 = new Question();
            question1.setText("What is H2O?");
            question1.setTest(test);
            questionRepository.save(question1);

            Question question2 = new Question();
            question2.setText("What is the chemical symbol for Gold?");
            question2.setTest(test);
            questionRepository.save(question2);
            
            System.out.println("Created questions for test");
        }

        System.out.println("Data initialization completed");
    }
}