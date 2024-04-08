package com.parkingcomestrue.parking.domain.searchcondition;

import lombok.Getter;

@Getter
public enum FeeType implements SearchConditionAvailable {

    FREE("무료"), PAID("유료"), NO_INFO("정보 없음");

    private final String description;

    FeeType(String description) {
        this.description = description;
    }

    @Override
    public FeeType getDefault() {
        return NO_INFO;
    }
}
