package com.example.qck.repository;

import com.example.qck.model.QuestionOption;
import com.example.qck.model.TestQuestion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestQuestionRepository extends JpaRepository <TestQuestion, Long> {
}
