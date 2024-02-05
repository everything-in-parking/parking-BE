package com.example.parking.domain.parking;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Embeddable
public class FeePolicy {

    @AttributeOverride(name = "fee", column = @Column(name = "base_fee"))
    @Embedded
    private Fee baseFee;

    @AttributeOverride(name = "fee", column = @Column(name = "extra_fee"))
    @Embedded
    private Fee extraFee;

    @AttributeOverride(name = "timeUnit", column = @Column(name = "base_time_unit"))
    @Embedded
    private TimeUnit baseTimeUnit;

    @AttributeOverride(name = "timeUnit", column = @Column(name = "extra_time_unit"))
    @Embedded
    private TimeUnit extraTimUnit;

    @AttributeOverride(name = "fee", column = @Column(name = "day_maximum_fee"))
    @Embedded
    private Fee dayMaximumFee;

    public FeePolicy(Fee baseFee, Fee extraFee, TimeUnit baseTimeUnit, TimeUnit extraTimUnit, Fee dayMaximumFee) {
        this.baseFee = baseFee;
        this.extraFee = extraFee;
        this.baseTimeUnit = baseTimeUnit;
        this.extraTimUnit = extraTimUnit;
        this.dayMaximumFee = dayMaximumFee;
    }

    public Fee calculateFee(int oneDayMinutes) {
        if (baseTimeUnit.isEqualOrGreaterThan(oneDayMinutes)) {
            return baseFee;
        }
        oneDayMinutes = oneDayMinutes - baseTimeUnit.getTimeUnit();
        int time = extraTimUnit.calculateQuotient(oneDayMinutes);
        return Fee.min(extraFee.multiply(time).plus(baseFee), dayMaximumFee);
    }
}
