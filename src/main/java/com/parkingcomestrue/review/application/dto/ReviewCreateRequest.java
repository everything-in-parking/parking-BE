package com.parkingcomestrue.review.application.dto;

import com.parkingcomestrue.review.domain.Content;
import java.util.List;

public record ReviewCreateRequest(List<String> contents) {

    public List<Content> toContents() {
        return contents.stream()
                .map(Content::find)
                .toList();
    }
}
