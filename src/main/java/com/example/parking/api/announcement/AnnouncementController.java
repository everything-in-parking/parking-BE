package com.example.parking.api.announcement;

import com.example.parking.domain.announcement.Announcement;
import com.example.parking.domain.announcement.AnnouncementRepository;
import com.example.parking.domain.announcement.AnnouncementType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AnnouncementController {

    private final AnnouncementRepository announcementRepository;

    public AnnouncementController(AnnouncementRepository announcementRepository) {
        this.announcementRepository = announcementRepository;
    }

    @GetMapping("/announcements")
    public ResponseEntity<Page<Announcement>> findAnnouncements(@RequestParam String type, Pageable pageable) {
        AnnouncementType announcementType = AnnouncementType.findType(type);
        Page<Announcement> announcements = announcementRepository.findAllByAnnouncementType(announcementType, pageable);
        return ResponseEntity.ok(announcements);
    }
}
