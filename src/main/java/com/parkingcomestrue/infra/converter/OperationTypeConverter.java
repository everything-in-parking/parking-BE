package com.parkingcomestrue.infra.converter;

import com.parkingcomestrue.parking.domain.OperationType;
import jakarta.persistence.Converter;

@Converter
public class OperationTypeConverter extends EnumListConverter<OperationType> {

    public OperationTypeConverter() {
        super(OperationType.class);
    }
}
