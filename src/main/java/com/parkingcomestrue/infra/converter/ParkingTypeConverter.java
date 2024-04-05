package com.parkingcomestrue.infra.converter;

import com.parkingcomestrue.parking.domain.ParkingType;
import jakarta.persistence.Converter;

@Converter
public class ParkingTypeConverter extends EnumListConverter<ParkingType> {

    public ParkingTypeConverter() {
        super(ParkingType.class);
    }
}
