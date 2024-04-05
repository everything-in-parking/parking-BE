package com.parkingcomestrue.parking.application.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class ParkingSearchConditionRequest {

    private final List<String> operationTypes;
    private final List<String> parkingTypes;
    private final String feeType;
    private final List<String> payTypes;
    private final Integer hours;
    private final String priority;

    public ParkingSearchConditionRequest(List<String> operationTypes, List<String> parkingTypes, String feeType,
                                         List<String> payTypes, int hours, String priority) {
        this.operationTypes = operationTypes;
        this.parkingTypes = parkingTypes;
        this.feeType = feeType;
        this.payTypes = payTypes;
        this.hours = hours;
        this.priority = priority;
    }
}
