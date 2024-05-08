package com.parkingcomestrue.common.infra.converter;

import com.parkingcomestrue.common.domain.parking.OperationType;
import jakarta.persistence.Converter;

@Converter
public class OperationTypeConverter extends EnumsConverter<OperationType> {

    public OperationTypeConverter() {
        super(OperationType.class);
    }
}
