package com.parkingcomestrue.parking.application.review.dto;

import com.parkingcomestrue.common.domain.review.Content;
import com.parkingcomestrue.common.support.exception.DomainException;
import com.parkingcomestrue.common.support.exception.DomainExceptionInformation;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public record ReviewCreateRequest(List<String> contents) {

    public Set<Content> toContents() {
        if (contents == null) {
            throw new DomainException(DomainExceptionInformation.INVALID_CONTENT);
        }
        return contents.stream()
                .map(Content::find)
                .collect(Collectors.toSet());
    }
}
