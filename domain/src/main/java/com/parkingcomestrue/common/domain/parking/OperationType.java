package com.parkingcomestrue.common.domain.parking;

import com.parkingcomestrue.common.domain.searchcondition.SearchConditionAvailable;
import java.util.Arrays;
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

    public static OperationType find(String description) {
        return Arrays.stream(values())
                .filter(e -> description.contains(e.getDescription()))
                .findAny()
                .orElse(NO_INFO);
    }

    @Override
    public OperationType getDefault() {
        return NO_INFO;
    }
}
