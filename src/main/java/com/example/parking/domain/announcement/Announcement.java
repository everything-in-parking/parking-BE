package com.example.parking.domain.announcement;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String fileName;
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private AnnouncementType announcementType;

    public Announcement(String title, String fileName, LocalDateTime createdAt, AnnouncementType announcementType) {
        this.title = title;
        this.fileName = fileName;
        this.createdAt = createdAt;
        this.announcementType = announcementType;
    }
}
