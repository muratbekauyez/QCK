package com.example.qck.service;

import com.example.qck.model.LearningObjective;
import com.example.qck.repository.LearningObjectiveRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LearningObjectiveService {

    private final LearningObjectiveRepository learningObjectiveRepository;

    public List<LearningObjective> getAllLearningObjectives(){
        return learningObjectiveRepository.findAll();
    }

    public void saveLearningObjective(LearningObjective learningObjective){
        learningObjectiveRepository.save(learningObjective);
    }
}
