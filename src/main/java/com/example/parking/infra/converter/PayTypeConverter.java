package com.example.parking.infra.converter;

import com.example.parking.domain.parking.PayType;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PayTypeConverter extends EnumListConverter<PayType> {

    public PayTypeConverter() {
        super(PayType.class);
    }
}
