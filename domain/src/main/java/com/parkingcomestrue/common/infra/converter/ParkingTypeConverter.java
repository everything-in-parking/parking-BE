package com.parkingcomestrue.common.infra.converter;

import com.parkingcomestrue.common.domain.parking.ParkingType;
import jakarta.persistence.Converter;

@Converter
public class ParkingTypeConverter extends EnumsConverter<ParkingType> {

    public ParkingTypeConverter() {
        super(ParkingType.class);
    }
}
