//package com.example.qck.controller;
//
//import com.example.qck.model.TestQuestion;
//import com.example.qck.repository.LearningObjectiveRepository;
//import com.example.qck.service.TestQuestionService;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//@Controller
//@RequestMapping("/testQuestions")
//@AllArgsConstructor
//public class TestQuestionController {
//
//    private final TestQuestionService testQuestionService;
//    private final LearningObjectiveRepository learningObjectiveRepository;
//
//    @GetMapping
//    public String testQuestionPage(Model model){
//        model.addAttribute("listTestQuestions", testQuestionService.getAllTestQuestions());
//        return "tests/test_questions";
//    }
//
//    @GetMapping("/new")
//    public String showNewTestQuestionForm(Model model){
//        TestQuestion testQuestion = new TestQuestion();
//        model.addAttribute("testQuestion", testQuestion);
//        model.addAttribute("learningObjectivesBySubject", learningObjectiveRepository.findAllBySubjectId(1L));
//        return "tests/new_test_question";
//    }
//
//    @PostMapping("/saveTestQuestion")
//    public String saveTestQuestion(@ModelAttribute("testQuestion") TestQuestion testQuestion){
//
//        testQuestionService.saveTestQuestion(testQuestion);
//        return "redirect:/testQuestions";
//    }
//
//    @GetMapping("/edit/{id}")
//    public String showUpdateTestQuestionForm(@PathVariable(value = "id")Long id, Model model){
//        TestQuestion testQuestion = testQuestionService.getTestQuestionById(id);
//        model.addAttribute("testQuestion", testQuestion);
//        model.addAttribute("learningObjectivesBySubject", learningObjectiveRepository.findAllBySubjectId(1L));
//        return "tests/update_test_question";
//    }
//
//    @PostMapping("/updateTestQuestion")
//    public String updateTestQuestion(@ModelAttribute("testQuestion") TestQuestion testQuestion){
//        testQuestionService.updateTestQuestion(testQuestion);
//        return "redirect:/testQuestions";
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteTestQuestions(@PathVariable(value = "id")Long id){
//        testQuestionService.deleteById(id);
//        return "redirect:/testQuestions";
//    }
//
//}
