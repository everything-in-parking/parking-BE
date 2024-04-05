package com.parkingcomestrue.searchcondition.domain;

import lombok.Getter;

@Getter
public enum Priority implements SearchConditionAvailable {

    DISTANCE("가까운순"),
    PRICE("가격순"),
    RECOMMENDATION("추천순");

    private final String description;

    Priority(String description) {
        this.description = description;
    }

    @Override
    public Priority getDefault() {
        return RECOMMENDATION;
    }
}
