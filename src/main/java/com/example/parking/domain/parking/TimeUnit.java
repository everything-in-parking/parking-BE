package com.example.parking.domain.parking;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class TimeUnit {

    private static final TimeUnit NO_INFO = new TimeUnit(-1);

    private int timeUnit;

    private TimeUnit(int timeUnit) {
        this.timeUnit = timeUnit;
    }

    public static TimeUnit from(int timeUnit) {
        if (timeUnit == 0) {
            return NO_INFO;
        }
        return new TimeUnit(timeUnit);
    }

    public static TimeUnit from(String timeUnit) {
        try {
            return TimeUnit.from(Integer.parseInt(timeUnit));
        } catch (NumberFormatException | NullPointerException e) {
            return NO_INFO;
        }
    }

    public boolean isEqualOrGreaterThan(int other) {
        return timeUnit >= other;
    }

    public int calculateQuotient(int minutes) {
        if (minutes <= 0) {
            return 0;
        }
        return (minutes - 1) / timeUnit + 1;
    }

    public boolean isValidTimeUnit() {
        return !NO_INFO.equals(this);
    }
}
