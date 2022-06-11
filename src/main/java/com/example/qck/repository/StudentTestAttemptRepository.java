package com.example.qck.repository;

import com.example.qck.model.StudentTestAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentTestAttemptRepository extends JpaRepository<StudentTestAttempt, Long> {
    List<StudentTestAttempt> findAllByTestId(Long testId);

    Optional<StudentTestAttempt> findStudentTestAttemptByTestIdAndUserId(Long testId, Long userId);
}
