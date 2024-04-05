package com.parkingcomestrue.infra.converter;

import com.parkingcomestrue.searchcondition.domain.FeeType;
import jakarta.persistence.Converter;

@Converter
public class FeeTypeConverter extends EnumListConverter<FeeType> {

    public FeeTypeConverter() {
        super(FeeType.class);
    }
}
