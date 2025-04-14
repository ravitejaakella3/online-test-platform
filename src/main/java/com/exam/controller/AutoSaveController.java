package com.exam.controller;

import com.exam.model.StudentAnswer;
import com.exam.model.Question;
import com.exam.service.AutoSaveService;
import com.exam.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Enable CORS for frontend integration
@Validated
public class AutoSaveController {

    @Autowired
    private AutoSaveService autoSaveService;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/autosave")
    public ResponseEntity<?> saveStudentAnswers(@Valid @RequestBody AutoSaveRequest request) {
        System.out.println("=== Auto-save Request ===");
        System.out.println("Student ID: " + request.getStudentId());
        System.out.println("Test ID: " + request.getTestId());
        System.out.println("Number of answers: " + request.getAnswers().size());
        
        // Verify question exists first
        Question question = questionRepository.findById(request.getAnswers().get(0).getQuestionId())
            .orElse(null);
        System.out.println("Question found: " + (question != null ? "yes" : "no"));
        if (question != null) {
            System.out.println("Question details - id: " + question.getId() 
                + ", testId: " + question.getTest().getId());
        }

        // Continue with validation
        for (StudentAnswer answer : request.getAnswers()) {
            boolean exists = questionRepository.existsByIdAndTestId(
                answer.getQuestionId(), request.getTestId());
            System.out.println("Question " + answer.getQuestionId() 
                + " exists in test " + request.getTestId() + ": " + exists);
            
            if (!exists) {
                return ResponseEntity.badRequest()
                    .body("Question " + answer.getQuestionId() 
                        + " does not belong to test " + request.getTestId());
            }
        }

        try {
            List<StudentAnswer> savedAnswers = autoSaveService.saveOrUpdateAnswers(
                request.getStudentId(),
                request.getTestId(),
                request.getAnswers()
            );
            System.out.println("Answers saved successfully. Count: " + savedAnswers.size());
            return ResponseEntity.ok(savedAnswers);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            System.err.println("Error saving answers: " + e.getMessage());
            throw e;
        }
    }

    @GetMapping("/questions/test/{testId}")
    public ResponseEntity<?> getQuestionsByTest(@PathVariable Long testId) {
        try {
            System.out.println("Fetching questions for testId: " + testId);
            List<Question> questions = questionRepository.findByTestId(testId);
            System.out.println("Found " + questions.size() + " questions for test " + testId);
            
            if (questions.isEmpty()) {
                System.out.println("No questions found for test " + testId);
                return ResponseEntity.notFound().build();
            }
            
            // Debug output
            questions.forEach(q -> System.out.println("Question: id=" + q.getId() + 
                ", text=" + q.getText() + ", testId=" + q.getTest().getId()));
                
            return ResponseEntity.ok(questions);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError()
                .body("Error fetching questions: " + e.getMessage());
        }
    }

    public static class AutoSaveRequest {
        private Long studentId;
        private Long testId;
        private List<StudentAnswer> answers;

        // Getters and Setters
        public Long getStudentId() {
            return studentId;
        }

        public void setStudentId(Long studentId) {
            this.studentId = studentId;
        }

        public Long getTestId() {
            return testId;
        }

        public void setTestId(Long testId) {
            this.testId = testId;
        }

        public List<StudentAnswer> getAnswers() {
            return answers;
        }

        public void setAnswers(List<StudentAnswer> answers) {
            this.answers = answers;
        }
    }
}