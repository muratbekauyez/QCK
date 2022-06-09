package com.example.qck.controller;

import com.example.qck.service.AnnouncementService;
import com.example.qck.model.Announcement;
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
@RequestMapping("/announcements")
@AllArgsConstructor
public class AnnouncementController {

    @Autowired
    private final AnnouncementService announcementService;

    @GetMapping
    public String announcementsPage(Model model){
        model.addAttribute("listAnnouncements", announcementService.getAllAnnouncements());
        return "announcements/announcements";
    }

    @GetMapping("/new")
    public String showNewAnnouncementForm(Model model){
        Announcement announcement = new Announcement();
        model.addAttribute("announcement", announcement);
        return "announcements/new_announcement";
    }

    @PostMapping("/saveAnnouncement")
    public String saveAnnouncement(@RequestParam Map<String, String> requestParams, @RequestParam("content") MultipartFile multipartFile, RedirectAttributes ra) throws IOException {
        announcementService.saveAnnouncement(requestParams, multipartFile, ra);
        return "redirect:/announcements";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateAnnouncementForm(@PathVariable(value = "id") Long id, Model model){
        Announcement announcement = announcementService.getAnnouncementById(id);
        model.addAttribute("announcement", announcement);
        return "announcements/update_announcement";
    }

    @PostMapping("/updateAnnouncement")
    public String updateAnnouncement(@ModelAttribute("announcement") Announcement announcement){
        announcementService.updateAnnouncement(announcement);
        return "redirect:/announcements";
    }



    @GetMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id")Long id){
        announcementService.deleteById(id);
        return "redirect:/announcements";
    }

    @GetMapping("/download")
    public void downloadFile(@Param("id")Long id, HttpServletResponse response) throws Exception {
        announcementService.downloadFile(id, response);
    }

    @GetMapping("/openFile")
    public void openFile(@Param("id")Long id, HttpServletResponse response) throws Exception {
        announcementService.openFile(id, response);
    }


}
