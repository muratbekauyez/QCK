package com.example.qck.controller;

import com.example.qck.service.ExamService;
import com.example.qck.model.Exam;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/exams")
@AllArgsConstructor
public class ExamController {

    @Autowired
    private final ExamService examService;

    @GetMapping()
    public String examsPage (Model model){
        model.addAttribute("listExams", examService.getAllExams());
        return "exams/exams";
    }

    @GetMapping("/new")
    public String showNewExamForm(Model model){
        Exam exam = new Exam();
        model.addAttribute("exam", exam);
        return "exams/new_exam";
    }

    @PostMapping("/saveExam")
    public String saveExam(@RequestParam Map<String, String> requestParams, @RequestParam("content")MultipartFile multipartFile, RedirectAttributes ra) throws IOException {
        System.out.println("test");
        examService.saveExam(requestParams, multipartFile, ra);
        return "redirect:/exams";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateExamForm(@PathVariable(value = "id") Long id, Model model){
        Exam exam = examService.getExamById(id);
        model.addAttribute("exam", exam);
        return "exams/update_exam";
    }

    @PostMapping("/updateExam")
    public String updateExam(@ModelAttribute("exam") Exam exam){
        examService.updateExam(exam);
        return "redirect:/exams";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id")Long id){
        examService.deleteById(id);
        return "redirect:/exams";
    }

    @GetMapping("/download")
    public void downloadFile(@Param("id")Long id, HttpServletResponse response) throws Exception {
        examService.downloadFile(id, response);
    }


}
