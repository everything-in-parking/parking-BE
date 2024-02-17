package com.example.parking.domain.searchcondition;

import com.example.parking.domain.parking.ParkingType;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class ParkingTypeConverter extends EnumListConverter<ParkingType> {

    public ParkingTypeConverter() {
        super(ParkingType.class);
    }
}
