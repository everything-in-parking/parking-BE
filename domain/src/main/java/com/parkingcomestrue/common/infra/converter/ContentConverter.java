package com.parkingcomestrue.common.infra.converter;

import com.parkingcomestrue.common.domain.review.Content;
import jakarta.persistence.Converter;

@Converter
public class ContentConverter extends EnumsConverter<Content> {

    protected ContentConverter() {
        super(Content.class);
    }
}
