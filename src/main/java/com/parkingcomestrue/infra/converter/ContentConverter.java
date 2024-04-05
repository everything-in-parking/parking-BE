package com.parkingcomestrue.infra.converter;

import com.parkingcomestrue.review.domain.Content;
import jakarta.persistence.Converter;

@Converter
public class ContentConverter extends EnumListConverter<Content> {

    protected ContentConverter() {
        super(Content.class);
    }
}
