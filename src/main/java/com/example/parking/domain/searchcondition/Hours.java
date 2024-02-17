package com.example.parking.domain.searchcondition;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Hours {

    private int hours;

    private Hours(int hours) {
        this.hours = hours;
    }

    public static Hours from(int hours) {
        if ((hours >= 1 && hours <= 12) || hours == 24) {
            return new Hours(hours);
        }
        throw new IllegalArgumentException("이용 시간은 1~12, 24 시간까지만 선택할 수 있습니다.");
    }
}
