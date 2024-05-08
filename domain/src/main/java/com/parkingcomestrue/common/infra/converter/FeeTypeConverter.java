package com.parkingcomestrue.common.infra.converter;

import com.parkingcomestrue.common.domain.searchcondition.FeeType;
import jakarta.persistence.Converter;

@Converter
public class FeeTypeConverter extends EnumsConverter<FeeType> {

    public FeeTypeConverter() {
        super(FeeType.class);
    }
}
