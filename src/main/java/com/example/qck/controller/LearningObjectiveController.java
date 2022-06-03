package com.example.qck.controller;

import com.example.qck.model.LearningObjective;
import com.example.qck.repository.SubjectRepository;
import com.example.qck.service.LearningObjectiveService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/learningObjectives")
@AllArgsConstructor
public class LearningObjectiveController {

    private final LearningObjectiveService learningObjectiveService;
    private final SubjectRepository subjectRepository;

    @GetMapping
    public String learningObjectivesPage(Model model){
        model.addAttribute("listLearningObjectives", learningObjectiveService.getAllLearningObjectives());
        return "learning_objectives/learning_objectives";
    }

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
        return "redirect:/learningObjectives";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateLearningObjectiveForm(@PathVariable(value = "id")Long id, Model model){
        LearningObjective learningObjective = learningObjectiveService.getLearningObjectiveById(id);
        model.addAttribute("learningObjective", learningObjective);
        model.addAttribute("allSubjects", subjectRepository.findAll());
        return "learning_objectives/update_learning_objective";
    }

    @PostMapping("/updateLearningObjective")
    public String updateLearningObjective(@ModelAttribute("learningObjective")LearningObjective learningObjective){
        learningObjectiveService.updateLearningObjective(learningObjective);
        return "redirect:/learningObjectives";
    }

}
