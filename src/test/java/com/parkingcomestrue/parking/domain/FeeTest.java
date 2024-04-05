package com.parkingcomestrue.parking.domain;

import com.parkingcomestrue.parking.domain.Fee;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class FeeTest {

    @Test
    void 단위_요금에_시간을_곱한다() {
        // given
        Fee fee = Fee.from(500);
        int time = 10;
        Fee expected = Fee.from(5000);

        // when
        Fee actual = fee.multiply(time);

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 요금끼리_더한다() {
        // given
        Fee fee = Fee.from(500);
        Fee otherFee = Fee.from(500);
        Fee expected = Fee.from(1000);

        // when
        Fee actual = fee.plus(otherFee);

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
