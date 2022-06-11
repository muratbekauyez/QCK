package com.example.qck.service;

import com.example.qck.model.LearningObjective;
import com.example.qck.repository.LearningObjectiveRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class LearningObjectiveService {

    private final LearningObjectiveRepository learningObjectiveRepository;

    public List<LearningObjective> getAllLearningObjectives(){
        return learningObjectiveRepository.findAll();
    }

    public List<LearningObjective> getAllEnabledLearningObjectives(){
        return learningObjectiveRepository.findLearningObjectiveByEnabled(true);
    }

    public void saveLearningObjective(LearningObjective learningObjective){
        learningObjective.setEnabled(true);
        learningObjectiveRepository.save(learningObjective);
    }

    public LearningObjective getLearningObjectiveById(Long id){
        Optional<LearningObjective> learningObjective = learningObjectiveRepository.findById(id);
        if(learningObjective.isEmpty()) throw new RuntimeException("Announcement not found for id: " + id);
        return learningObjective.get();
    }

    public void updateLearningObjective(LearningObjective learningObjective){
        learningObjective.setEnabled(true);
        learningObjectiveRepository.save(learningObjective);
    }

    public void disableTest(Long id) {
        LearningObjective learningObjective = getLearningObjectiveById(id);
        learningObjective.setEnabled(false);
        learningObjectiveRepository.save(learningObjective);
    }
}
