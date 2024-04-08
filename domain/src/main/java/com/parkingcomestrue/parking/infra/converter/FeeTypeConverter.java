package com.parkingcomestrue.parking.infra.converter;

import com.parkingcomestrue.parking.domain.searchcondition.FeeType;
import jakarta.persistence.Converter;

@Converter
public class FeeTypeConverter extends EnumListConverter<FeeType> {

    public FeeTypeConverter() {
        super(FeeType.class);
    }
}
