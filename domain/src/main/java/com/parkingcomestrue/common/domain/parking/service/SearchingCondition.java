package com.parkingcomestrue.common.domain.parking.service;

import com.parkingcomestrue.common.domain.parking.OperationType;
import com.parkingcomestrue.common.domain.parking.ParkingType;
import com.parkingcomestrue.common.domain.parking.PayType;
import com.parkingcomestrue.common.domain.searchcondition.FeeType;
import java.util.Set;
import lombok.Getter;

@Getter
public class SearchingCondition {

    private final Set<OperationType> operationTypes;
    private final Set<ParkingType> parkingTypes;
    private final Set<PayType> payTypes;
    private final FeeType feeType;
    private final Integer hours;

    public SearchingCondition(Set<OperationType> operationTypes, Set<ParkingType> parkingTypes,
                              Set<PayType> payTypes,
                              FeeType feeType, Integer hours) {
        this.operationTypes = operationTypes;
        this.parkingTypes = parkingTypes;
        this.payTypes = payTypes;
        this.feeType = feeType;
        this.hours = hours;
    }
}
