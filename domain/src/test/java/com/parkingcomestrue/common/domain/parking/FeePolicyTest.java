package com.parkingcomestrue.common.domain.parking;

import com.parkingcomestrue.common.domain.parking.Fee;
import com.parkingcomestrue.common.domain.parking.FeePolicy;
import com.parkingcomestrue.common.domain.parking.TimeUnit;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FeePolicyTest {

    @ParameterizedTest
    @MethodSource("getFee")
    void 주어진_시간의_요금을_계산한다(FeePolicy feePolicy, int minutes, Fee expected) {
        // given, when
        Fee actual = feePolicy.calculateFee(minutes);

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> getFee() {
        return Stream.of(
                Arguments.of(
                        new FeePolicy(
                                Fee.from(2000),
                                Fee.from(2000),
                                TimeUnit.from(15),
                                TimeUnit.from(15),
                                Fee.from(10000)),
                        60,
                        Fee.from(8000)
                ),
                Arguments.of(
                        new FeePolicy(
                                Fee.from(2000),
                                Fee.from(2000),
                                TimeUnit.from(15),
                                TimeUnit.from(15),
                                Fee.from(10000)),
                        2000,
                        Fee.from(10000)
                ),
                Arguments.of(
                        new FeePolicy(
                                Fee.from(0),
                                Fee.from(2000),
                                TimeUnit.from(15),
                                TimeUnit.from(15),
                                Fee.from(10000)),
                        46,
                        Fee.from(6000)
                ),
                Arguments.of(
                        new FeePolicy(
                                Fee.from(1000),
                                Fee.from(2000),
                                TimeUnit.from(15),
                                TimeUnit.from(15),
                                Fee.from(10000)),
                        46,
                        Fee.from(7000)
                ),
                Arguments.of(
                        new FeePolicy(
                                Fee.from(2000),
                                Fee.from(2000),
                                TimeUnit.from(30),
                                TimeUnit.from(15),
                                Fee.from(55000)),
                        180,
                        Fee.from(22000)
                ),
                Arguments.of(
                        new FeePolicy(
                                Fee.from(2000),
                                Fee.from(2000),
                                TimeUnit.from(30),
                                TimeUnit.from(15),
                                Fee.from(55000)),
                        0,
                        Fee.from(0)
                )
        );
    }
}
