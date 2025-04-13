package com.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.exam.model.Test;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {
}