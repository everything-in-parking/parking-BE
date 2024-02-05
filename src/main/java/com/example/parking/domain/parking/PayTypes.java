package com.example.parking.domain.parking;

import jakarta.persistence.Embeddable;
import java.util.Collection;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class PayTypes {

    public static final PayTypes DEFAULT = new PayTypes(PayType.NO_INFO.getDescription());
    private static final String DELIMITER = ", ";

    private String description;

    private PayTypes(String description) {
        this.description = description;
    }

    public PayTypes from(Collection<PayType> payTypes) {
        if (payTypes.contains(PayType.NO_INFO)) {
            return DEFAULT;
        }

        return new PayTypes(payTypes.stream()
                .map(PayType::getDescription)
                .sorted()
                .collect(Collectors.joining(DELIMITER))
        );
    }
}
