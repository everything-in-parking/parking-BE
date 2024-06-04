package com.parkingcomestrue.common.domain.parking;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode(of = "fee")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class Fee {

    public static final Fee ZERO = new Fee(0);
    public static final Fee NO_INFO = new Fee(-1);

    private Integer fee;

    private Fee(Integer fee) {
        this.fee = fee;
    }

    public static Fee from(Integer fee) {
        return new Fee(fee);
    }

    public static Fee from(String fee) {
        try {
            return new Fee(Integer.parseInt(fee));
        } catch (NumberFormatException | NullPointerException e) {
            return NO_INFO;
        }
    }

    public Fee multiply(int time) {
        return new Fee(fee * time);
    }

    public Fee plus(Fee fee) {
        return new Fee(this.fee + fee.fee);
    }

    public static Fee min(Fee fee, Fee otherFee) {
        if (fee.fee < otherFee.fee) {
            return fee;
        }
        return otherFee;
    }

    public boolean isFree() {
        return fee == 0;
    }

    public boolean isValidFee() {
        return !NO_INFO.equals(this);
    }
}
