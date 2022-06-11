package com.example.qck.controller;

import com.example.qck.config.MyUserDetails;
import com.example.qck.model.LearningObjective;
import com.example.qck.model.Subject;
import com.example.qck.model.User;
import com.example.qck.repository.SubjectRepository;
import com.example.qck.repository.UserRepository;
import com.example.qck.service.LearningObjectiveService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/learningObjectives")
@AllArgsConstructor
public class LearningObjectiveController {

    private final LearningObjectiveService learningObjectiveService;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;

    @GetMapping
    public String learningObjectivesPage(Model model){
        model.addAttribute("listLearningObjectives", learningObjectiveService.getAllEnabledLearningObjectives());
        return "learning_objectives/learning_objectives";
    }

    @GetMapping("/new")
    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    public String showNewLearningObjectiveForm(Model model, @AuthenticationPrincipal MyUserDetails myUserDetails){
        User user = userRepository.getUserByUsername(myUserDetails.getUsername());
        LearningObjective learningObjective = new LearningObjective();
        model.addAttribute("learningObjective", learningObjective);
        if(user.getSubject() != null){
            List<Subject> subject = new ArrayList<>();
            subject.add(user.getSubject());
            model.addAttribute("allSubjects", subject);
        }else {
            model.addAttribute("allSubjects", subjectRepository.findAll());
        }
        return "learning_objectives/new_learning_objective";
    }

    @PostMapping("/saveLearningObjective")
    public String saveLearningObjective(@ModelAttribute("learningObjective") LearningObjective learningObjective){
        learningObjectiveService.saveLearningObjective(learningObjective);
        return "redirect:/learningObjectives";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    public String showUpdateLearningObjectiveForm(@PathVariable(value = "id")Long id, Model model,
                                                  @AuthenticationPrincipal MyUserDetails myUserDetails){
        User user = userRepository.getUserByUsername(myUserDetails.getUsername());
        LearningObjective learningObjective = learningObjectiveService.getLearningObjectiveById(id);
        model.addAttribute("learningObjective", learningObjective);
        if(user.getSubject() != null){
            List<Subject> subject = new ArrayList<>();
            subject.add(user.getSubject());
            model.addAttribute("allSubjects", subject);
        }else {
            model.addAttribute("allSubjects", subjectRepository.findAll());
        }
        return "learning_objectives/update_learning_objective";
    }

    @PostMapping("/updateLearningObjective")
    public String updateLearningObjective(@ModelAttribute("learningObjective")LearningObjective learningObjective){
        learningObjectiveService.updateLearningObjective(learningObjective);
        return "redirect:/learningObjectives";
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','TEACHER')")
    public String disableTest(@PathVariable Long id){
        learningObjectiveService.disableTest(id);
        return "redirect:/learningObjectives";
    }

}
