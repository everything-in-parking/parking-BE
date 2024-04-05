package com.parkingcomestrue.parking.dto;

import com.parkingcomestrue.parking.domain.OperationType;
import com.parkingcomestrue.parking.domain.ParkingType;
import com.parkingcomestrue.parking.domain.PayTypes;
import lombok.Getter;

@Getter
public class ParkingQueryCondition {

    private OperationType operationType;
    private ParkingType parkingType;
    private Boolean cardEnabled;
    private PayTypes payTypes;

    public ParkingQueryCondition(OperationType operationType, ParkingType parkingType, Boolean cardEnabled,
                                 PayTypes payTypes) {
        this.operationType = operationType;
        this.parkingType = parkingType;
        this.cardEnabled = cardEnabled;
        this.payTypes = payTypes;
    }
}
