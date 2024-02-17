package com.example.parking.domain.searchcondition;

import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class FeeTypeConverter extends EnumListConverter<FeeType> {

    public FeeTypeConverter() {
        super(FeeType.class);
    }
}
