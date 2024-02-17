package com.example.parking.domain.parking;

import com.example.parking.domain.searchcondition.SearchConditionAvailable;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum ParkingType implements SearchConditionAvailable {
    OFF_STREET("노외"),
    ON_STREET("노상"),
    MECHANICAL("기계식"),
    NO_INFO("정보 없음");

    private static final Map<String, ParkingType> descriptions =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(ParkingType::getDescription, Function.identity())));

    private final String description;

    ParkingType(String description) {
        this.description = description;
    }

    private static boolean isSame(String input, String description) {
        if (input.isBlank()) {
            return false;
        }
        input = removeSpace(input);
        description = removeSpace(description);
        return description.startsWith(input);
    }

    private static String removeSpace(String description) {
        return description.replace(" ", "");

    @Override
    public ParkingType getDefault() {
        return NO_INFO;
    }
}
