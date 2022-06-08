package com.example.qck.service;

import com.example.qck.repository.ManualRepository;
import com.example.qck.model.Manual;
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
public class ManualService {

    private final ManualRepository manualRepository;

    public List<Manual> getAllManuals(){
        return manualRepository.findAll();
    }

    public void saveManual(Map<String, String> requestParams, MultipartFile multipartFile, RedirectAttributes ra) throws IOException {
        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        Manual manual = new Manual();
        manual.setTitle(requestParams.get("title"));
        manual.setText("text");
        manual.setFilename(filename);
        manual.setContent(multipartFile.getBytes());
        manual.setDate(new Date());

        ra.addFlashAttribute("message", "The file has been uploaded successfully.");
        manualRepository.save(manual);
    }


    public Manual getManualById(Long id){
        Optional<Manual> manual = manualRepository.findById(id);
        if(manual.isEmpty()) throw new RuntimeException("Manual not found for id :" + id);
        return manual.get();
    }

    public void updateManual(Manual manual) {
        manualRepository.save(manual);
    }

    public void deleteById(Long id) {
        manualRepository.deleteById(id);
    }

    public void downloadFile(Long id, HttpServletResponse response) throws Exception{
        Optional<Manual> optionalManual = manualRepository.findById(id);
        if(optionalManual.isEmpty()) throw new Exception("Could not find exam with ID:" + id);

        Manual manual = optionalManual.get();

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + manual.getFilename();
        response.setHeader(headerKey, headerValue);

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(manual.getContent());
        outputStream.close();
    }
}
