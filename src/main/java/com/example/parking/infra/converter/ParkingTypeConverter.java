package com.example.parking.infra.converter;

import com.example.parking.domain.parking.ParkingType;
import jakarta.persistence.Converter;

@Converter
public class ParkingTypeConverter extends EnumListConverter<ParkingType> {

    public ParkingTypeConverter() {
        super(ParkingType.class);
    }
}
