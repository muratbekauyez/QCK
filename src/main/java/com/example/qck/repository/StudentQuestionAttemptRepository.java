package com.example.qck.repository;

import com.example.qck.model.StudentQuestionAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentQuestionAttemptRepository extends JpaRepository<StudentQuestionAttempt, Long> {
}
