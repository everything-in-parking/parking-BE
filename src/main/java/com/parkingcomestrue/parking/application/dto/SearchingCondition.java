package com.parkingcomestrue.parking.application.dto;

import com.parkingcomestrue.parking.domain.OperationType;
import com.parkingcomestrue.parking.domain.ParkingType;
import com.parkingcomestrue.parking.domain.PayType;
import com.parkingcomestrue.searchcondition.domain.FeeType;
import java.util.List;
import lombok.Getter;

@Getter
public class SearchingCondition {

    private final List<OperationType> operationTypes;
    private final List<ParkingType> parkingTypes;
    private final List<PayType> payTypes;
    private final FeeType feeType;
    private final Integer hours;

    public SearchingCondition(List<OperationType> operationTypes, List<ParkingType> parkingTypes,
                              List<PayType> payTypes,
                              FeeType feeType, Integer hours) {
        this.operationTypes = operationTypes;
        this.parkingTypes = parkingTypes;
        this.payTypes = payTypes;
        this.feeType = feeType;
        this.hours = hours;
    }
}
