package com.parkingcomestrue.common.infra.converter;

import com.parkingcomestrue.common.domain.parking.Fee;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class FeeConverter implements AttributeConverter<Fee, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Fee attribute) {
        return attribute.getFee();
    }

    @Override
    public Fee convertToEntityAttribute(Integer dbData) {
        return Fee.from(dbData);
    }
}
