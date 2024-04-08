package com.parkingcomestrue.parking.domain.parking;

import com.parkingcomestrue.parking.domain.searchcondition.SearchConditionAvailable;
import lombok.Getter;

@Getter
public enum OperationType implements SearchConditionAvailable {

    PUBLIC("공영"),
    PRIVATE("민영"),
    NO_INFO("정보 없음");

    private final String description;

    OperationType(String description) {
        this.description = description;
    }

    @Override
    public OperationType getDefault() {
        return NO_INFO;
    }
}
