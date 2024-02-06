package com.example.parking.domain.parking;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;

@Getter
public enum ParkingType {
    OFF_STREET("노외 주차장"),
    ON_STREET("노상 주차장"),
    MECHANICAL("기계식 주차장"),
    NO_INFO("정보 없음");

    private static final Map<String, ParkingType> descriptions =
            Collections.unmodifiableMap(Stream.of(values())
                    .collect(Collectors.toMap(ParkingType::getDescription, Function.identity())));

    private final String description;

    ParkingType(String description) {
        this.description = description;
    }

    public static ParkingType find(String description) {
        if (descriptions.containsKey(description)) {
            return descriptions.get(description);
        }
        
        return descriptions.get(
                descriptions.keySet().stream()
                .filter(key -> isSame(description, key))
                .findAny()
                .orElse(NO_INFO.description)
        );
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
    }
}
