package com.example.qck.repository;

import com.example.qck.model.TestQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface TestQuestionRepository extends JpaRepository <TestQuestion, Long> {
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM tests_test_questions tq WHERE tq.question_id=:question_id", nativeQuery = true)
    void deleteTestQuestionsByIdFromTestTestQuestions(@Param("question_id") Long questionId);
}
