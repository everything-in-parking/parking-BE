package com.parkingcomestrue.parking.application.review.dto;

import com.parkingcomestrue.common.domain.review.Content;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record ReviewCreateRequest(List<String> contents) {

    public Set<Content> toContents() {
        return contents.stream()
                .map(Content::find)
                .collect(Collectors.toSet());
    }
}
