package com.parkingcomestrue.infra.converter;

import com.parkingcomestrue.parking.domain.PayType;
import jakarta.persistence.Converter;

@Converter
public class PayTypeConverter extends EnumListConverter<PayType> {

    public PayTypeConverter() {
        super(PayType.class);
    }
}
