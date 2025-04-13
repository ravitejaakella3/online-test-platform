package com.exam.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "student_answers")
public class StudentAnswer {
    public static final String STATUS_ATTEMPTED = "ATTEMPTED";
    public static final String STATUS_SKIPPED = "SKIPPED";
    public static final String STATUS_MARKED_FOR_REVIEW = "MARKED_FOR_REVIEW";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long studentId;

    @Column(nullable = false)
    private Long testId;

    @Column(nullable = false)
    private Long questionId;

    @Column(columnDefinition = "TEXT")
    private String response;

    @Column(length = 20)
    private String status; // ATTEMPTED, SKIPPED, MARKED_FOR_REVIEW

    @Column(nullable = false)
    private LocalDateTime timestamp;

    @Version
    private Long version; // For optimistic locking

    // Add constructor
    public StudentAnswer() {
        this.timestamp = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }
}