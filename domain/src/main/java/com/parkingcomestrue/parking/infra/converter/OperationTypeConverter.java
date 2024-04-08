package com.parkingcomestrue.parking.infra.converter;

import com.parkingcomestrue.parking.domain.parking.OperationType;
import jakarta.persistence.Converter;

@Converter
public class OperationTypeConverter extends EnumListConverter<OperationType> {

    public OperationTypeConverter() {
        super(OperationType.class);
    }
}
