package com.parkingcomestrue.common.domain.searchcondition;

public interface SearchConditionAvailable {

    String getDescription();

    <E extends SearchConditionAvailable> E getDefault();
}
