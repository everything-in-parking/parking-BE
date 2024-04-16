package com.parkingcomestrue.common.infra.converter;

import com.parkingcomestrue.common.domain.parking.PayType;
import jakarta.persistence.Converter;

@Converter
public class PayTypeConverter extends EnumListConverter<PayType> {

    public PayTypeConverter() {
        super(PayType.class);
    }
}
