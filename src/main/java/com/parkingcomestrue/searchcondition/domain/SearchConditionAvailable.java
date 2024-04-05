package com.parkingcomestrue.searchcondition.domain;

public interface SearchConditionAvailable {

    String getDescription();

    <E extends SearchConditionAvailable> E getDefault();
}
