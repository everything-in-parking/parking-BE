package com.parkingcomestrue.common.infra.converter;

import com.parkingcomestrue.common.domain.parking.TimeUnit;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class TimeUnitConverter implements AttributeConverter<TimeUnit, Integer> {

    @Override
    public Integer convertToDatabaseColumn(TimeUnit attribute) {
        return attribute.getTimeUnit();
    }

    @Override
    public TimeUnit convertToEntityAttribute(Integer dbData) {
        return TimeUnit.from(dbData);
    }
}
