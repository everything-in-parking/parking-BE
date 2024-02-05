package com.example.parking.domain.parking;

import static jakarta.persistence.EnumType.STRING;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Enumerated;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class BaseInformation {

    private String name;
    private String tel;
    private String address;

    @Embedded
    private PayTypes payTypes;

    @Enumerated(STRING)
    private ParkingType parkingType;

    @Enumerated(STRING)
    private OperationType operationType;

    public BaseInformation(String name, String tel, String address, PayTypes payTypes, ParkingType parkingType,
                           OperationType operationType) {
        this.name = name;
        this.tel = tel;
        this.address = address;
        this.payTypes = payTypes;
        this.parkingType = parkingType;
        this.operationType = operationType;
    }
}
