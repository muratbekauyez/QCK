package com.example.qck.service;

import com.example.qck.repository.AnnouncementRepository;
import com.example.qck.model.Announcement;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    public List<Announcement> getAllAnnouncements(){
        return announcementRepository.findAll();
    }

    public void saveAnnouncement(Announcement announcement){
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
}
