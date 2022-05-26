package com.example.qck.service;

import com.example.qck.model.TestQuestion;
import com.example.qck.repository.TestQuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TestQuestionService {

    private final TestQuestionRepository testQuestionRepository;

    public void saveTestQuestion (TestQuestion testQuestion){
        testQuestionRepository.save(testQuestion);
    }
}
