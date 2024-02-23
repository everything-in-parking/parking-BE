package com.example.parking.application.review.dto;

import com.example.parking.domain.review.Content;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public record ReviewCreateRequest(List<String> contents, List<MultipartFile> images) {

    public List<Content> toContents() {
        return contents.stream()
                .map(Content::find)
                .toList();
    }
}
