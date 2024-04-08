package com.parkingcomestrue.parking.infra.converter;

import com.parkingcomestrue.parking.domain.parking.ParkingType;
import jakarta.persistence.Converter;

@Converter
public class ParkingTypeConverter extends EnumListConverter<ParkingType> {

    public ParkingTypeConverter() {
        super(ParkingType.class);
    }
}
