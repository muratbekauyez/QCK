package com.example.qck.service;

import com.example.qck.repository.ExamRepository;
import com.example.qck.model.Exam;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ExamService {

    private ExamRepository examRepository;

    public List<Exam> getAllExams(){
        return examRepository.findAll();
    }

    public void saveExam(Map<String, String> requestParams, MultipartFile multipartFile, RedirectAttributes ra) throws IOException {
        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        Exam exam = new Exam();
        exam.setTitle(requestParams.get("title"));
        exam.setDescription(requestParams.get("description"));
        exam.setFilename(filename);
        exam.setContent(multipartFile.getBytes());
        exam.setUploadTime(new Date());

        ra.addFlashAttribute("message", "The file has been uploaded successfully.");
        examRepository.save(exam);
    }

    public Exam getExamById(Long id){
        Optional<Exam> exam = examRepository.findById(id);
        if(exam.isEmpty()) throw new RuntimeException("Exam not found for id: " + id);
        return exam.get();
    }

    public void updateExam(Exam exam){
        examRepository.save(exam);
    }

    public void deleteById(Long id){
        examRepository.deleteById(id);
    }

    public void downloadFile(Long id, HttpServletResponse response) throws Exception {
        Optional<Exam> optionalExam = examRepository.findById(id);
        if(optionalExam.isEmpty()) throw new Exception("Could not find exam with ID: " + id);

        Exam exam = optionalExam.get();

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + exam.getFilename();
        response.setHeader(headerKey, headerValue);

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(exam.getContent());
        outputStream.close();


    }
}
