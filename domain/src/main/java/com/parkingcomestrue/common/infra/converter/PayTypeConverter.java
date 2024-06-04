package com.parkingcomestrue.common.infra.converter;

import com.parkingcomestrue.common.domain.parking.PayType;
import jakarta.persistence.Converter;

@Converter
public class PayTypeConverter extends EnumsConverter<PayType> {

    public PayTypeConverter() {
        super(PayType.class);
    }
}
