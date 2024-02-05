package com.example.parking.domain.parking;

import static jakarta.persistence.EnumType.*;
import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Enumerated;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = PROTECTED)
@Embeddable
public class BaseInformation {

    private String name;
    private String tel;
    private String address;

    @Embedded
    private PayTypes payType;

    @Enumerated(STRING)
    private ParkingType parkingType;

    @Enumerated(STRING)
    private OperationType operationType;

    public BaseInformation(String name, String tel, String address, PayTypes payType, ParkingType parkingType,
                           OperationType operationType) {
        this.name = name;
        this.tel = tel;
        this.address = address;
        this.payType = payType;
        this.parkingType = parkingType;
        this.operationType = operationType;
    }
}
