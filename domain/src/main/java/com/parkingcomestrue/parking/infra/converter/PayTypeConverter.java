package com.parkingcomestrue.parking.infra.converter;

import com.parkingcomestrue.parking.domain.parking.PayType;
import jakarta.persistence.Converter;

@Converter
public class PayTypeConverter extends EnumListConverter<PayType> {

    public PayTypeConverter() {
        super(PayType.class);
    }
}
