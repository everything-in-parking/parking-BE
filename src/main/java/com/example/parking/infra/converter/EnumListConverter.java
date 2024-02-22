package com.example.parking.infra.converter;

import jakarta.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public abstract class EnumListConverter<E extends Enum<E>> implements AttributeConverter<List<E>, String> {

    private static final String DELIMITER = ", ";

    private final Class<E> enumClass;

    protected EnumListConverter(Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public String convertToDatabaseColumn(List<E> attribute) {
        return attribute.stream()
                .map(Enum::name)
                .sorted()
                .collect(Collectors.joining(DELIMITER));
    }

    @Override
    public List<E> convertToEntityAttribute(String dbData) {
        return Arrays.stream(dbData.split(DELIMITER))
                .map(name -> Enum.valueOf(enumClass, name))
                .toList();
    }
}
