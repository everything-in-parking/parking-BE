package com.example.parking.domain.parking;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
@Getter
public class TimeUnit {

    private int timeUnit;

    private TimeUnit(int timeUnit) {
        this.timeUnit = timeUnit;
    }

    public static TimeUnit from(int timeUnit) {
        return new TimeUnit(timeUnit);
    }

    public boolean isEqualOrGreaterThan(int other) {
        if (timeUnit >= other) {
            return true;
        }
        return false;
    }

    public int calculateQuotient(int minutes) {
        if (minutes <= 0) {
            return 0;
        }
        return (minutes - 1) / timeUnit + 1;
    }
}
