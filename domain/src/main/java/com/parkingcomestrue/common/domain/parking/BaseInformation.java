package com.parkingcomestrue.common.domain.parking;

import static jakarta.persistence.EnumType.STRING;

import com.parkingcomestrue.common.infra.converter.PayTypeConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class BaseInformation {

    private static final String DELIMITER = ", ";

    private String name;
    private String tel;
    private String address;

    @Convert(converter = PayTypeConverter.class)
    private List<PayType> payTypes;

    @Enumerated(STRING)
    private ParkingType parkingType;

    @Enumerated(STRING)
    private OperationType operationType;

    public BaseInformation(String name, String tel, String address, List<PayType> payTypes, ParkingType parkingType,
                           OperationType operationType) {
        this.name = name;
        this.tel = tel;
        this.address = address;
        this.payTypes = payTypes;
        this.parkingType = parkingType;
        this.operationType = operationType;
    }

    public boolean containsOperationType(List<OperationType> operationTypes) {
        return operationTypes.stream()
                .anyMatch(operationType -> this.operationType == operationType);
    }

    public boolean containsParkingType(List<ParkingType> parkingTypes) {
        return parkingTypes.stream()
                .anyMatch(parkingType -> this.parkingType == parkingType);
    }

    public boolean containsPayType(List<PayType> memberPayTypes) {
        if (memberPayTypes.contains(PayType.NO_INFO)) {
            return true;
        }
        return memberPayTypes.stream()
                .anyMatch(payType -> this.payTypes.contains(payType));
    }

    public String getPayTypesDescription() {
        return payTypes.stream()
                .map(PayType::getDescription)
                .sorted()
                .collect(Collectors.joining(DELIMITER));
    }

    public String getPayTypesName() {
        return payTypes.stream()
                .map(PayType::name)
                .sorted()
                .collect(Collectors.joining(DELIMITER));
    }
}
