package com.parkingcomestrue.parking.application.review.dto;

import com.parkingcomestrue.common.domain.review.Content;
import java.util.List;

public record ReviewCreateRequest(List<String> contents) {

    public List<Content> toContents() {
        return contents.stream()
                .map(Content::find)
                .toList();
    }
}
