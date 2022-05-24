package com.example.qck.announcement;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String saveAnnouncement(@ModelAttribute("announcement") Announcement announcement){
        announcementService.saveAnnouncement(announcement);
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


}
