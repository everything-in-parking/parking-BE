package com.parkingcomestrue.parking.domain.review;

import com.parkingcomestrue.parking.support.exception.DomainException;
import com.parkingcomestrue.parking.support.exception.ExceptionInformation;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum Content {

    LOTS_OF_PARKING_SPOTS("주차 자리가 많아요"),
    GOOD_CONDITION("깔끔하고 관리가 잘 되어 있어요"),
    GOOD_CONSIDERATE_SPOT("주차 배려석이 잘 되어 있어요"),
    LOTS_OF_CHARGING_SPOTS("전기차 충전석이 많아요"),
    LARGE_PARKING_SPACE("주차석이 널널해서 주차가 편해요"),
    CLOSE_TO_MAIN_LOAD("큰 길과 가까워요"),
    GOOD_ACCESSIBILITY("접근성이 좋아요"),
    EASY_TO_PAY("결제가 편리해요"),
    FRIENDLY_STAFF("직원이 친절해요"),
    LOW_PRICE("가격이 저렴해요");

    private final String description;

    Content(String description) {
        this.description = description;
    }

    public static Content find(String description) {
        return Arrays.stream(values())
                .filter(content -> content.getDescription().startsWith(description))
                .findAny()
                .orElseThrow(() -> new DomainException(ExceptionInformation.INVALID_CONTENT));
    }
}
