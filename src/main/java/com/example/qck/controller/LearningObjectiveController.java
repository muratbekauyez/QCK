package com.example.qck.controller;

import com.example.qck.model.LearningObjective;
import com.example.qck.model.Subject;
import com.example.qck.repository.SubjectRepository;
import com.example.qck.service.LearningObjectiveService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/learningObjectives")
@AllArgsConstructor
public class LearningObjectiveController {

    private final LearningObjectiveService learningObjectiveService;
    private final SubjectRepository subjectRepository;

    @GetMapping("/new")
    public String showNewLearningObjectiveForm(Model model){
        LearningObjective learningObjective = new LearningObjective();
        model.addAttribute("learningObjective", learningObjective);
        model.addAttribute("allSubjects", subjectRepository.findAll());
        return "learning_objectives/new_learning_objective";
    }

    @PostMapping("/saveLearningObjective")
    public String saveLearningObjective(@ModelAttribute("learningObjective") LearningObjective learningObjective){
        learningObjectiveService.saveLearningObjective(learningObjective);
        return "redirect:/";
    }

}
