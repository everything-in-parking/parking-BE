package com.example.parking.infra.converter;

import com.example.parking.domain.review.Content;

public class ContentConverter extends EnumListConverter<Content> {

    protected ContentConverter() {
        super(Content.class);
    }
}
