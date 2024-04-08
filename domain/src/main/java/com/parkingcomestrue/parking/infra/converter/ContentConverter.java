package com.parkingcomestrue.parking.infra.converter;

import com.parkingcomestrue.parking.domain.review.Content;
import jakarta.persistence.Converter;

@Converter
public class ContentConverter extends EnumListConverter<Content> {

    protected ContentConverter() {
        super(Content.class);
    }
}
