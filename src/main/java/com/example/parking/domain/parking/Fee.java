package com.example.parking.domain.parking;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Fee {

    public static final Fee ZERO = new Fee(0);

    private Integer fee;

    private Fee(Integer fee) {
        this.fee = fee;
    }

    public static Fee from(Integer fee) {
        return new Fee(fee);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fee fee1 = (Fee) o;
        return Objects.equals(fee, fee1.fee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fee);
    }
}
