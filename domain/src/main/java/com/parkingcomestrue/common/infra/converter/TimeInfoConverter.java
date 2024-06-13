package com.parkingcomestrue.common.infra.converter;

import com.parkingcomestrue.common.domain.parking.TimeInfo;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Converter
public class TimeInfoConverter implements AttributeConverter<TimeInfo, String> {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final String DELIMITER = "~";

    @Override
    public String convertToDatabaseColumn(TimeInfo attribute) {
        return attribute.toString();
    }

    @Override
    public TimeInfo convertToEntityAttribute(String dbData) {
        String[] times = dbData.split(DELIMITER);
        return new TimeInfo(LocalTime.parse(times[0], TIME_FORMATTER), LocalTime.parse(times[1], TIME_FORMATTER));
    }
}
