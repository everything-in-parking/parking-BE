package com.example.parking.domain.searchcondition;

import java.util.Arrays;

public interface SearchConditionAvailable {

    String getDescription();

    <E extends SearchConditionAvailable> E getDefault();

    static <E extends SearchConditionAvailable> E find(String description, E... values) {
        return Arrays.stream(values)
                .filter(e -> description.startsWith(e.getDescription()))
                .findAny()
                .orElse(values[0].getDefault());
    }
}
