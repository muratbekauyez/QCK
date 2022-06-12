package com.example.qck.service;

import com.example.qck.model.*;
import com.example.qck.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class AnalyticsService {

    private final TestRepository testRepository;
    private final TestQuestionRepository testQuestionRepository;
    private final LearningObjectiveRepository learningObjectiveRepository;
    private final StudentQuestionAttemptRepository studentQuestionAttemptRepository;

    public Pair<List<String>, HashMap<String, HashMap<Double, Integer>>> getAnalyticsForSubjectById(Long subject_id){

        HashMap<String, HashMap<Double, Integer>> results = new HashMap<>();
        List<LearningObjective> learningObjectives = learningObjectiveRepository.findAllBySubjectId(subject_id);
        List<String> lo_codes = new ArrayList<>();

        for(LearningObjective lo: learningObjectives){
            HashMap<Double, Integer> res = new HashMap<>();
            List<Object[]> tmp = studentQuestionAttemptRepository.getAnalytics(lo.getId());
            lo_codes.add(lo.getCode());
            for(Object[] x: tmp){
                Integer val = res.putIfAbsent((Double) x[0], 1);
                if (val != null)
                    res.put((Double) x[0], val + 1);
            }
            results.put(lo.getCode(), res);
        }


        return Pair.of(lo_codes,results);
    }




}
