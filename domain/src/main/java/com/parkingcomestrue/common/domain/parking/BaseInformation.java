package com.parkingcomestrue.common.domain.parking;

import static jakarta.persistence.EnumType.STRING;

import com.parkingcomestrue.common.infra.converter.PayTypeConverter;
import com.parkingcomestrue.common.support.exception.DomainException;
import com.parkingcomestrue.common.support.exception.DomainExceptionInformation;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;
import java.util.Set;
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
    private Set<PayType> payTypes;

    @Enumerated(STRING)
    private ParkingType parkingType;

    @Enumerated(STRING)
    private OperationType operationType;

    public BaseInformation(String name, String tel, String address, Set<PayType> payTypes, ParkingType parkingType,
                           OperationType operationType) {
        validatePayTypes(payTypes);
        this.name = name;
        this.tel = tel;
        this.address = address;
        this.payTypes = payTypes;
        this.parkingType = parkingType;
        this.operationType = operationType;
    }

    private void validatePayTypes(Set<PayType> payTypes) {
        if (payTypes == null || payTypes.isEmpty() || payTypes.size() >= PayType.values().length) {
            throw new DomainException(DomainExceptionInformation.INVALID_PAY_TYPES_SIZE);
        }
    }

    public boolean containsOperationType(Set<OperationType> operationTypes) {
        return operationTypes.stream()
                .anyMatch(operationType -> this.operationType == operationType);
    }

    public boolean containsParkingType(Set<ParkingType> parkingTypes) {
        return parkingTypes.stream()
                .anyMatch(parkingType -> this.parkingType == parkingType);
    }

    public boolean containsPayType(Set<PayType> memberPayTypes) {
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
