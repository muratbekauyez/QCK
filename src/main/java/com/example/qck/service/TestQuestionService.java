package com.example.qck.service;

import com.example.qck.model.QuestionType;
import com.example.qck.model.TestQuestion;
import com.example.qck.repository.QuestionOptionRepository;
import com.example.qck.repository.TestQuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TestQuestionService {

    private final TestQuestionRepository testQuestionRepository;
    private final QuestionOptionRepository questionOptionRepository;

    public List<TestQuestion> getAllTestQuestions(){
        return testQuestionRepository.findAll();
    }

    public void saveTestQuestion (TestQuestion testQuestion){
        testQuestionRepository.save(testQuestion);
    }

    public TestQuestion getTestQuestionById(Long id){
        Optional<TestQuestion> testQuestion = testQuestionRepository.findById(id);
        if(testQuestion.isEmpty()) throw new RuntimeException("Test Question not found for id: " + id);
        return testQuestion.get();
    }

    public void updateTestQuestion(TestQuestion testQuestion){
        testQuestionRepository.save(testQuestion);
    }



    public void deleteById(Long id) {
        Long questionOptionId = testQuestionRepository.getOne(id).getQuestionOption().getId();
        testQuestionRepository.deleteById(id);
        questionOptionRepository.deleteById(questionOptionId);
    }
}
