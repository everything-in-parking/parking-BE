package com.parkingcomestrue.parking.domain.searchcondition;

import com.parkingcomestrue.parking.support.exception.DomainException;
import com.parkingcomestrue.parking.support.exception.DomainExceptionInformation;
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
        throw new DomainException(DomainExceptionInformation.INVALID_HOURS);
    }
}
