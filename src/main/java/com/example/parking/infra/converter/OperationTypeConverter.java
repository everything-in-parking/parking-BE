package com.example.parking.infra.converter;

import com.example.parking.domain.parking.OperationType;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class OperationTypeConverter extends EnumListConverter<OperationType> {

    public OperationTypeConverter() {
        super(OperationType.class);
    }
}
