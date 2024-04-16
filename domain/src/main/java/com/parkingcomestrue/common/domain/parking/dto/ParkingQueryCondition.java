package com.parkingcomestrue.common.domain.parking.dto;

import com.parkingcomestrue.common.domain.parking.OperationType;
import com.parkingcomestrue.common.domain.parking.ParkingType;
import com.parkingcomestrue.common.domain.parking.PayTypes;
import lombok.Getter;

@Getter
public class ParkingQueryCondition {

    private final OperationType operationType;
    private final ParkingType parkingType;
    private final Boolean cardEnabled;
    private final PayTypes payTypes;

    public ParkingQueryCondition(OperationType operationType, ParkingType parkingType, Boolean cardEnabled,
                                 PayTypes payTypes) {
        this.operationType = operationType;
        this.parkingType = parkingType;
        this.cardEnabled = cardEnabled;
        this.payTypes = payTypes;
    }
}
