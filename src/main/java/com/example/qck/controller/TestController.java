package com.example.qck.controller;

import com.example.qck.config.MyUserDetails;
import com.example.qck.model.*;
import com.example.qck.repository.LearningObjectiveRepository;
import com.example.qck.repository.UserRepository;
import com.example.qck.service.LearningObjectiveService;
import com.example.qck.service.TeacherService;
import com.example.qck.service.TestService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/tests")
@AllArgsConstructor
public class TestController {

    private final TestService testService;
    private final TeacherService teacherService;
    private final LearningObjectiveService learningObjectiveService;
    private final LearningObjectiveRepository learningObjectiveRepository;
    private final UserRepository userRepository;

    @GetMapping
    public String testsPage(Model model){
        model.addAttribute("listTests", testService.getAllEnabledTest());
        return "tests/tests";
    }

    @GetMapping("/new")
    public String showNewTestForm(Model model){
        Test test = new Test();
        model.addAttribute("test", test);
        return "tests/new_test";
    }

    @PostMapping("/saveTest")
    public String saveTest (@ModelAttribute("test") Test test){
        testService.saveTest(test);
        return "redirect:/tests";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateTestForm(@PathVariable Long id, Model model){
        Test test = testService.getTestById(id);
        model.addAttribute("test", test);
        return "tests/update_test";
    }

    @PostMapping("/updateTest")
    public String updateTest(@ModelAttribute("test")Test test){
        testService.updateTest(test);
        return "redirect:/tests";
    }

    @GetMapping("/delete/{id}")
    public String disableTest(@PathVariable Long id){
        testService.disableTest(id);
        return "redirect:/tests";
    }

    @GetMapping("/{id}")
    public String testPage(@PathVariable(value = "id")Long id, Model model){
        Test test = testService.getTestById(id);
        model.addAttribute("test", test);
        return "tests/test_page";
    }

    @GetMapping("/{id}/newQuestion")
    public String showNewTestQuestionForm(@PathVariable(value = "id")Long id, Model model,
                                          @AuthenticationPrincipal MyUserDetails myUserDetails){
        TestQuestion testQuestion = new TestQuestion();
        User user = userRepository.getUserByUsername(myUserDetails.getUsername());
        model.addAttribute("testQuestion", testQuestion);
        model.addAttribute("testId", id);
        if(user.getSubject() != null) {
            model.addAttribute("learningObjectivesBySubject",
                    learningObjectiveRepository.findAllBySubjectIdAndEnabled(user.getSubject().getId(), true));
        } else {
            model.addAttribute("learningObjectivesBySubject",
                    learningObjectiveService.getAllEnabledLearningObjectives());
        }
        return "tests/new_test_question";
    }

    @PostMapping("/saveTestQuestion")
    public String saveTestQuestion(@RequestParam("testId")Long testId,
                                   @ModelAttribute("testQuestion") TestQuestion testQuestion,
                                   Model model){
        Test test = testService.addTestQuestion(testId, testQuestion);
        model.addAttribute("test", test);
        return "redirect:/tests/" + testId;
    }


    @GetMapping("{id}/editQuestion/{questionId}")
    public String showUpdateQuestionForm(@PathVariable(value = "id")Long id,
                                         @PathVariable(value = "questionId") Long questionId,
                                         Model model){
        TestQuestion testQuestion = testService.getTestQuestionById(questionId);
        model.addAttribute("testId", id);
        model.addAttribute("testQuestion", testQuestion);
        model.addAttribute("learningObjectivesBySubject", learningObjectiveRepository.findAllBySubjectIdAndEnabled(1L, true));
        return "tests/update_test_question";
    }

    @PostMapping("/updateTestQuestion")
    public String updateTestQuestion(@RequestParam("testId")Long testId,
                                     @RequestParam("questionId")Long questionId,
                                     @ModelAttribute("testQuestion") TestQuestion testQuestion,
                                     Model model){
        testService.updateTestQuestion(testQuestion, questionId);
        Test test = testService.getTestById(testId);
        model.addAttribute("test", test);
        return "redirect:/tests/" + testId;
    }

    @GetMapping("{id}/deleteQuestion/{questionId}")
    public String deleteTestQuestion(@PathVariable(value = "id") Long id,
                                     @PathVariable(value = "questionId") Long questionId){
        testService.deleteByQuestionId(questionId);
        return "redirect:/tests/" + id;
    }

    @GetMapping("/takeExam/{id}")
    public String takeExam(@PathVariable Long id,
                           @AuthenticationPrincipal MyUserDetails user,
                           Model model){
        model.addAttribute("user", user);
        model.addAttribute("test", testService.getTestById(id));
        model.addAttribute("studentTestAttempt", new StudentTestAttempt());
        return "tests/student_test_attempt";
    }

    @PostMapping("/passTest")
    public String passTest(@ModelAttribute("test")Test test,
                         @AuthenticationPrincipal MyUserDetails user,
                           Model model){
        testService.saveStudentTestAttempt(test, user);
        model.addAttribute("listTests", testService.getAllTests());
        return "redirect:/tests";
    }
    
    @GetMapping("/{id}/studentsAttempts")
    public String allStudentAttempts(@PathVariable("id") Long testId,
                                     Model model){
        model.addAttribute("studentAttempts", testService.allStudentTestAttemptsByTest(testId));
        return "tests/students_attempts";
    }

    @GetMapping("/studentAttempt/{id}")
    public String studentAttempt(@PathVariable("id") Long attemptId,
                                 Model model){
        model.addAttribute("studentAttempt", testService.getStudentTestAttempt(attemptId));
        model.addAttribute("teacherCheck", new TeacherCheck());
        return "tests/student_attempt_result";
    }

    @PostMapping("/checkTest")
    public String checkTest(@ModelAttribute("studentAttempt")StudentTestAttempt testAttempt,
                            @AuthenticationPrincipal MyUserDetails user){
        teacherService.saveChecking(testAttempt, user);
        return "redirect:/tests/studentAttempt/" + testAttempt.getId();
    }

}
