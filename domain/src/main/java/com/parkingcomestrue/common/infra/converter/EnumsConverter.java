package com.parkingcomestrue.common.infra.converter;

import jakarta.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class EnumsConverter<E extends Enum<E>> implements AttributeConverter<Set<E>, String> {

    private static final String DELIMITER = ", ";

    private final Class<E> enumClass;

    protected EnumsConverter(Class<E> enumClass) {
        this.enumClass = enumClass;
    }

    @Override
    public String convertToDatabaseColumn(Set<E> attribute) {
        return attribute.stream()
                .map(Enum::name)
                .sorted()
                .collect(Collectors.joining(DELIMITER));
    }

    @Override
    public Set<E> convertToEntityAttribute(String dbData) {
        return Arrays.stream(dbData.split(DELIMITER))
                .map(name -> Enum.valueOf(enumClass, name))
                .collect(Collectors.toSet());
    }
}
