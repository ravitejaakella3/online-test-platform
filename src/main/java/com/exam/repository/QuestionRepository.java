package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.exam.model.Question;
import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    
    @Query("SELECT DISTINCT q FROM Question q JOIN FETCH q.test t WHERE t.id = :testId")
    List<Question> findByTestId(@Param("testId") Long testId);

    @Query(value = "SELECT EXISTS(SELECT 1 FROM questions q WHERE q.id = :id AND q.test_id = :testId)", 
           nativeQuery = true)
    boolean existsByIdAndTestId(@Param("id") Long id, @Param("testId") Long testId);
}