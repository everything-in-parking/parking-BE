package com.parkingcomestrue.parking.application.searchcondition.dto;

import java.util.List;
import lombok.Getter;

@Getter
public class SearchConditionDto {

    private List<String> operationType;
    private List<String> parkingType;
    private List<String> feeType;
    private List<String> payType;
    private String priority;
    private Integer hours;

    public SearchConditionDto(List<String> operationType, List<String> parkingType, List<String> feeType,
                              List<String> payType, String priority, Integer hours) {
        this.operationType = operationType;
        this.parkingType = parkingType;
        this.feeType = feeType;
        this.payType = payType;
        this.priority = priority;
        this.hours = hours;
    }
}
