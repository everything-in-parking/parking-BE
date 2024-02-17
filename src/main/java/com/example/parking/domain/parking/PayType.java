package com.example.parking.domain.parking;

import com.example.parking.domain.searchcondition.SearchConditionAvailable;
import lombok.Getter;

@Getter
public enum PayType implements SearchConditionAvailable {

    CASH("현금"),
    CARD("카드"),
    BANK_TRANSFER("계좌"),
    NO_INFO("정보 없음");

    private final String description;

    PayType(final String description) {
        this.description = description;
    }

    @Override
    public PayType getDefault() {
        return NO_INFO;
    }
}

