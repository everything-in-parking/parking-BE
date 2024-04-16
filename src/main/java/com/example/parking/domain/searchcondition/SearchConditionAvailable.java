package com.example.parking.domain.searchcondition;

public interface SearchConditionAvailable {

    String getDescription();

    <E extends SearchConditionAvailable> E getDefault();
}
