package com.example.parking.domain.searchcondition;

import com.example.parking.domain.parking.PayType;
import jakarta.persistence.Converter;

@Converter(autoApply = true)
public class PayTypeConverter extends EnumListConverter<PayType> {

    public PayTypeConverter() {
        super(PayType.class);
    }
}
