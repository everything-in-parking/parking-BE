package com.example.parking.domain.announcement;

import com.example.parking.application.member.dto.MemberNotFoundException;

import java.util.Arrays;

public enum AnnouncementType {

    NOTICE("notice"),
    EVENT("event");

    private final String type;

    AnnouncementType(String type) {
        this.type = type;
    }

    public static AnnouncementType findType(String type) {
        return Arrays.stream(AnnouncementType.values())
                .filter(announcementType -> announcementType.getType().equals(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("찾을 수 없는 타입"));
    }

    public String getType() {
        return type;
    }
}
