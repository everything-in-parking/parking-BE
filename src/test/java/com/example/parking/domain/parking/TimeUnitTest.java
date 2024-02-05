package com.example.parking.domain.parking;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TimeUnitTest {

    @Test
    void 시간_단위의_대소_비교한다() {
        // given
        TimeUnit timeUnit = TimeUnit.from(100);
        int otherTimeUnit = 1000;
        boolean expected = false;

        // when
        boolean actual = timeUnit.isEqualOrGreaterThan(otherTimeUnit);

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"10:60:6", "10:59:6"}, delimiter = ':')
    void 시간을_단위_시간으로_나눈_몫을_구한다(int timeUnit, int time, int expected) {
        // given
        TimeUnit unit = TimeUnit.from(timeUnit);

        // when
        int actual = unit.calculateQuotient(time);

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
