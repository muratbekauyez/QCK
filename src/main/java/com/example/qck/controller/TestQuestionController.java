package com.example.qck.controller;

import com.example.qck.model.TestQuestion;
import com.example.qck.repository.LearningObjectiveRepository;
import com.example.qck.service.TestQuestionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/testQuestions")
@AllArgsConstructor
public class TestQuestionController {

    private final TestQuestionService testQuestionService;
    private final LearningObjectiveRepository learningObjectiveRepository;

    @GetMapping("/new")
    public String showNewTestQuestionForm(Model model){
        TestQuestion testQuestion = new TestQuestion();
        model.addAttribute("testQuestion", testQuestion);
        model.addAttribute("learningObjectivesBySubject", learningObjectiveRepository.findAllBySubjectId(1L));
        return "tests/new_test_question";
    }

    @PostMapping("/saveTestQuestion")
    public String saveTestQuestion(@ModelAttribute("testQuestion") TestQuestion testQuestion){
        testQuestionService.saveTestQuestion(testQuestion);
        return "redirect:/";
    }

}
