package com.example.qck.controller;

import com.example.qck.repository.SubjectRepository;
import com.example.qck.service.AnalyticsService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/analytics")
@AllArgsConstructor

public class AnalyticsController {

    @Autowired
    private final AnalyticsService analyticsService;
    private final SubjectRepository subjectRepository;

    @GetMapping
    public String analyticsPage(@RequestParam(required = false) Long subject_id, @RequestParam(required = false) String chartType, Model model){
        model.addAttribute("subjects", subjectRepository.findAll());

        if (subject_id != null) {
            Pair<List<String>, HashMap<String, HashMap<Double, Integer>>> data = analyticsService.getAnalyticsForSubjectById(subject_id);
            if (chartType == null) chartType = "line";
            model.addAttribute("subjectName",subjectRepository.getById(subject_id));
            model.addAttribute("loList", data.getFirst());
            model.addAttribute("analyticsMap", data.getSecond());
            model.addAttribute("chartType", chartType);
        }
        return "analytics/analytics";
    }


}
