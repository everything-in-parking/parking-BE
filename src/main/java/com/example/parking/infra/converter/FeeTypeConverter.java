package com.example.parking.infra.converter;

import com.example.parking.domain.searchcondition.FeeType;
import jakarta.persistence.Converter;

@Converter
public class FeeTypeConverter extends EnumListConverter<FeeType> {

    public FeeTypeConverter() {
        super(FeeType.class);
    }
}
