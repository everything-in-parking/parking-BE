package com.parkingcomestrue.searchcondition.domain;

import com.parkingcomestrue.support.exception.DomainException;
import com.parkingcomestrue.support.exception.ExceptionInformation;
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
        throw new DomainException(ExceptionInformation.INVALID_HOURS);
    }
}
