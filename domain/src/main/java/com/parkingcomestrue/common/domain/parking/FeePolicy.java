package com.parkingcomestrue.common.domain.parking;

import com.parkingcomestrue.common.infra.converter.FeeConverter;
import com.parkingcomestrue.common.infra.converter.TimeUnitConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class FeePolicy {

    @Convert(converter = FeeConverter.class)
    private Fee baseFee;

    @Convert(converter = FeeConverter.class)
    private Fee extraFee;

    @Convert(converter = TimeUnitConverter.class)
    private TimeUnit baseTimeUnit;

    @Convert(converter = TimeUnitConverter.class)
    private TimeUnit extraTimeUnit;

    @Convert(converter = FeeConverter.class)
    private Fee dayMaximumFee;

    public FeePolicy(Fee baseFee, Fee extraFee, TimeUnit baseTimeUnit, TimeUnit extraTimeUnit, Fee dayMaximumFee) {
        this.baseFee = baseFee;
        this.extraFee = extraFee;
        this.baseTimeUnit = baseTimeUnit;
        this.extraTimeUnit = extraTimeUnit;
        this.dayMaximumFee = dayMaximumFee;
    }

    public Fee calculateFee(int minutes) {
        if (supportBase()) {
            return calculateFeeWithBase(minutes);
        }
        return calculateFeeWithoutBase(minutes);
    }

    public boolean supportBase() {
        return baseFee.isValidFee() && baseTimeUnit.isValidTimeUnit();
    }

    public boolean supportExtra() {
        return extraFee.isValidFee() && extraTimeUnit.isValidTimeUnit();
    }

    private Fee calculateFeeWithBase(int minutes) {
        if (minutes == 0) {
            return Fee.ZERO;
        }
        if (baseTimeUnit.isEqualOrGreaterThan(minutes)) {
            return baseFee;
        }
        minutes = minutes - baseTimeUnit.getTimeUnit();
        int time = extraTimeUnit.calculateQuotient(minutes);
        return Fee.min(extraFee.multiply(time).plus(baseFee), dayMaximumFee);
    }

    private Fee calculateFeeWithoutBase(int minutes) {
        if (minutes == 0) {
            return Fee.ZERO;
        }
        if (supportExtra()) {
            int time = extraTimeUnit.calculateQuotient(minutes);
            return Fee.min(extraFee.multiply(time), dayMaximumFee);
        }
        return Fee.NO_INFO;
    }
}
