package com.example.qck.repository;

import com.example.qck.model.StudentTestAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentTestAttemptRepository extends JpaRepository<StudentTestAttempt, Long> {
    List<StudentTestAttempt> findAllByTestId(Long testId);
}
