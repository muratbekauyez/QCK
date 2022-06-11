package com.example.qck.service;

import com.example.qck.repository.AnnouncementRepository;
import com.example.qck.model.Announcement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringReader;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public List<Announcement> getAllAnnouncements(){
        return announcementRepository.findAll();
    }

    public void saveAnnouncement(Map<String, String> requestParams, MultipartFile multipartFile, RedirectAttributes ra) throws IOException {
        String filename = StringUtils.cleanPath(multipartFile.getOriginalFilename());

        Announcement announcement = new Announcement();
        announcement.setTitle(requestParams.get("title"));
        announcement.setText("text");
        if(!multipartFile.isEmpty()) {
            announcement.setContent(multipartFile.getBytes());
            announcement.setFilename(filename);
        }
        announcement.setDate(new Date());

        ra.addFlashAttribute("message", "The file has been uploaded successfully.");
        announcementRepository.save(announcement);
    }


    public Announcement getAnnouncementById (Long id){
        Optional<Announcement> announcement = announcementRepository.findById(id);
        if(announcement.isEmpty()) throw new RuntimeException("Announcement not found for id: " + id);
        return announcement.get();
    }

    public void updateAnnouncement(Announcement announcement) {
        announcementRepository.save(announcement);
    }

    public void deleteById(Long id) {
        announcementRepository.deleteById(id);
    }

    public void downloadFile(Long id, HttpServletResponse response) throws Exception{
        Announcement announcement = getAnnouncementById(id);

        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + announcement.getFilename();
        response.setHeader(headerKey, headerValue);

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(announcement.getContent());
        outputStream.close();
    }

    public void openFile(Long id, HttpServletResponse response) throws Exception{
        Announcement announcement = getAnnouncementById(id);

        response.setContentType("application/pdf");
        String headerKey = "Content-Disposition,";
        String headerValue = "inline; filename=" + announcement.getFilename();
        response.setHeader(headerKey, headerValue);

        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(announcement.getContent());
        outputStream.close();
    }
}
