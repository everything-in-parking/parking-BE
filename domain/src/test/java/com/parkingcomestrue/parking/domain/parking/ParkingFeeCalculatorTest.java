package com.parkingcomestrue.parking.domain.parking;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ParkingFeeCalculatorTest {

    private final ParkingFeeCalculator parkingFeeCalculator = new ParkingFeeCalculator();

    @ParameterizedTest
    @MethodSource("getParkingFeeCalculator")
    void 주차요금을_계산한다(Parking parking, LocalDateTime beginTime, LocalDateTime endTime, Fee expected) {
        // given, when
        Fee actual = parkingFeeCalculator.calculateParkingFee(parking, beginTime, endTime);

        // then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> getParkingFeeCalculator() {
        return Stream.of(
                /*
                        주차장 정보
                        [무료 운영 정보]
                        주중  : 09:00 ~ 12:00 무료
                        토요일 : 00:00 ~ 24:00 무료
                        일요일 : 00:00 ~ 24:00 무료

                        [요금 정보]
                        기본 주차 요금 정책 : 30분당 2000원
                        추가 주차 요금 정책 : 15분당 2000원
                        하루 최대 요금     : 50000원

                        주차 정보
                        주차 시작 시각 : 2024/2/16/금/09:00
                        주차 종료 시각 : 2024/2/16/금/21:00

                        계산 주차 금액
                        1. 2/16 유료 주차 시간 : 2024/2/16/12:00 ~ 2024/2/16/21:00 (540분)
                        1. 2/16 주차 금액 : 2000원 + 68000원 = 70000원 -> 하루 최대 요금 적용 -> 50000원

                        주차 금액 합 : 50000원
                 */
                Arguments.of(
                        new Parking(
                                new BaseInformation(),
                                new Location(),
                                new Space(),
                                new FreeOperatingTime(
                                        new TimeInfo(
                                                LocalTime.of(9, 0),
                                                LocalTime.of(12, 0)
                                        ),
                                        TimeInfo.ALL_DAY,
                                        TimeInfo.ALL_DAY
                                ),
                                new OperatingTime(),
                                new FeePolicy(
                                        Fee.from(2000),
                                        Fee.from(2000),
                                        TimeUnit.from(30),
                                        TimeUnit.from(15),
                                        Fee.from(50000)
                                )
                        ),
                        LocalDateTime.of(2024, 2, 16, 9, 0),
                        LocalDateTime.of(2024, 2, 16, 21, 0),
                        Fee.from(50000)
                ),
                /*
                        주차장 정보
                        [무료 운영 정보]
                        주중  : 09:00 ~ 12:00 무료
                        토요일 : 00:00 ~ 24:00 무료
                        일요일 : 00:00 ~ 24:00 무료

                        [요금 정보]
                        기본 주차 요금 정책 : 30분당 2000원
                        추가 주차 요금 정책 : 15분당 2000원
                        하루 최대 요금     : 50000원

                        주차 정보
                        주차 시작 시각 : 2024/2/16/금/09:00
                        주차 종료 시각 : 2024/2/16/금/13:16

                        계산 주차 금액
                        1. 2/16 유료 주차 시간 : 2024/2/16/12:00 ~ 2024/2/16/13:16 (76분)
                        1. 2/16 주차 금액 : 2000원 + 8000원 = 10000원

                        주차 금액 합 : 10000원
                 */
                Arguments.of(
                        new Parking(
                                new BaseInformation(),
                                new Location(),
                                new Space(),
                                new FreeOperatingTime(
                                        new TimeInfo(
                                                LocalTime.of(9, 0),
                                                LocalTime.of(12, 0)
                                        ),
                                        TimeInfo.ALL_DAY,
                                        TimeInfo.ALL_DAY
                                ),
                                new OperatingTime(),
                                new FeePolicy(
                                        Fee.from(2000),
                                        Fee.from(2000),
                                        TimeUnit.from(30),
                                        TimeUnit.from(15),
                                        Fee.from(50000)
                                )
                        ),
                        LocalDateTime.of(2024, 2, 16, 9, 0),
                        LocalDateTime.of(2024, 2, 16, 13, 16),
                        Fee.from(10000)
                ),
                /*
                        주차장 정보
                        [무료 운영 정보]
                        주중  : 09:00 ~ 12:00 무료
                        토요일 : 00:00 ~ 24:00 무료
                        일요일 : 00:00 ~ 24:00 무료

                        [요금 정보]
                        기본 주차 요금 정책 : 30분당 2000원
                        추가 주차 요금 정책 : 15분당 2000원
                        하루 최대 요금     : 50000원

                        주차 정보
                        주차 시작 시각 : 2024/2/16/금/10:00
                        주차 종료 시각 : 2024/2/16/금/12:01

                        계산 주차 금액
                        1. 2/16 유료 주차 시간 : 2024/2/16/12:00 ~ 2024/2/16/12:01 (1분)
                        1. 2/16 주차 금액 : 2000원 (기본 요금)

                        주차 금액 합 : 2000원
                 */
                Arguments.of(
                        new Parking(
                                new BaseInformation(),
                                new Location(),
                                new Space(),
                                new FreeOperatingTime(
                                        new TimeInfo(
                                                LocalTime.of(9, 0),
                                                LocalTime.of(12, 0)
                                        ),
                                        TimeInfo.ALL_DAY,
                                        TimeInfo.ALL_DAY
                                ),
                                new OperatingTime(),
                                new FeePolicy(
                                        Fee.from(2000),
                                        Fee.from(2000),
                                        TimeUnit.from(30),
                                        TimeUnit.from(15),
                                        Fee.from(50000)
                                )
                        ),
                        LocalDateTime.of(2024, 2, 16, 10, 0),
                        LocalDateTime.of(2024, 2, 16, 12, 1),
                        Fee.from(2000)
                ),
                /*
                        주차장 정보
                        [무료 운영 정보]
                        주중  : 09:00 ~ 12:00 무료
                        토요일 : 00:00 ~ 24:00 무료
                        일요일 : 00:00 ~ 24:00 무료

                        [요금 정보]
                        기본 주차 요금 정책 : 30분당 2000원
                        추가 주차 요금 정책 : 15분당 2000원
                        하루 최대 요금     : 50000원

                        주차 정보
                        주차 시작 시각 : 2024/2/16/금/21:00
                        주차 종료 시각 : 2024/2/17/토/09:00

                        계산 주차 금액
                        1. 2/16 유료 주차 시간 : 2024/2/16/21:00 ~ 2024/2/16/24:00 (180분)
                        1. 2/16 주차 금액 : 2000원 + 20000원 = 22000원

                        2. 2/17 유료 주차 시간 : 없음
                        2. 2/17 주차 금액 : 0원

                        주차 금액 합 : 22000원
                 */
                Arguments.of(
                        new Parking(
                                new BaseInformation(),
                                new Location(),
                                new Space(),
                                new FreeOperatingTime(
                                        new TimeInfo(
                                                LocalTime.of(9, 0),
                                                LocalTime.of(12, 0)
                                        ),
                                        TimeInfo.ALL_DAY,
                                        TimeInfo.ALL_DAY
                                ),
                                new OperatingTime(),
                                new FeePolicy(
                                        Fee.from(2000),
                                        Fee.from(2000),
                                        TimeUnit.from(30),
                                        TimeUnit.from(15),
                                        Fee.from(50000)
                                )
                        ),
                        LocalDateTime.of(2024, 2, 16, 21, 0),
                        LocalDateTime.of(2024, 2, 17, 9, 0),
                        Fee.from(22000)
                ),
                /*
                        주차장 정보
                        [무료 운영 정보]
                        주중  : 09:00 ~ 12:00 무료
                        토요일 : 06:00 ~ 24:00 무료
                        일요일 : 00:00 ~ 24:00 무료

                        [요금 정보]
                        기본 주차 요금 정책 : 30분당 2000원
                        추가 주차 요금 정책 : 15분당 2000원
                        하루 최대 요금     : 50000원

                        주차 정보
                        주차 시작 시각 : 2024/2/16/금/21:00
                        주차 종료 시각 : 2024/2/17/토/09:00

                        계산 주차 금액
                        1. 2/16 유료 주차 시간 : 2024/2/16/21:00 ~ 2024/2/16/24:00 (180분)
                        1. 2/16 주차 금액 : 2000원 + 20000원 = 22000원

                        2. 2/17 유료 주차 시간 : 2024/2/17/00:00 ~ 2024/2/17/06:00 (360분)
                        2. 2/17 주차 금액 : 2000원 + 44000원 = 46000원

                        주차 금액 합 : 68000원
                 */
                Arguments.of(
                        new Parking(
                                new BaseInformation(),
                                new Location(),
                                new Space(),
                                new FreeOperatingTime(
                                        new TimeInfo(
                                                LocalTime.of(9, 0),
                                                LocalTime.of(12, 0)
                                        ),
                                        new TimeInfo(
                                                LocalTime.of(6, 0),
                                                LocalTime.MAX
                                        ),
                                        TimeInfo.ALL_DAY
                                ),
                                new OperatingTime(),
                                new FeePolicy(
                                        Fee.from(2000),
                                        Fee.from(2000),
                                        TimeUnit.from(30),
                                        TimeUnit.from(15),
                                        Fee.from(50000)
                                )
                        ),
                        LocalDateTime.of(2024, 2, 16, 21, 0),
                        LocalDateTime.of(2024, 2, 17, 9, 0),
                        Fee.from(68000)
                ),
                /*
                        주차장 정보
                        [무료 운영 정보]
                        주중  : 09:00 ~ 12:00 무료
                        토요일 : 00:00 ~ 24:00 무료
                        일요일 : 04:00 ~ 24:00 무료

                        [요금 정보]
                        기본 주차 요금 정책 : 30분당 2000원
                        추가 주차 요금 정책 : 15분당 2000원
                        하루 최대 요금     : 50000원

                        주차 정보
                        주차 시작 시각 : 2024/2/16/금/21:00
                        주차 종료 시각 : 2024/2/18/일/09:00

                        계산 주차 금액
                        1. 2/16 유료 주차 시간 : 2024/2/16/21:00 ~ 2024/2/16/24:00 (180분)
                        1. 2/16 주차 금액 : 2000원 + 20000원 = 22000원

                        2. 2/17 유료 주차 시간 : 없음
                        2. 2/17 주차 금액 : 0원

                        3. 2/18 유료 주차 시간 : 2024/2/18/00:00 ~ 2024/2/18/04:00 (240분)
                        3. 2/18 주차 금액 : 2000원 + 28000원 = 30000원

                        주차 금액 합 : 22000원 + 0원 + 30000원 = 52000원
                 */
                Arguments.of(
                        new Parking(
                                new BaseInformation(),
                                new Location(),
                                new Space(),
                                new FreeOperatingTime(
                                        new TimeInfo(
                                                LocalTime.of(9, 0),
                                                LocalTime.of(12, 0)
                                        ),
                                        TimeInfo.ALL_DAY,
                                        new TimeInfo(
                                                LocalTime.of(4, 0),
                                                LocalTime.MAX
                                        )
                                ),
                                new OperatingTime(),
                                new FeePolicy(
                                        Fee.from(2000),
                                        Fee.from(2000),
                                        TimeUnit.from(30),
                                        TimeUnit.from(15),
                                        Fee.from(50000)
                                )
                        ),
                        LocalDateTime.of(2024, 2, 16, 21, 0),
                        LocalDateTime.of(2024, 2, 18, 9, 0),
                        Fee.from(52000)
                ),
                /*
                        주차장 정보
                        [무료 운영 정보]
                        주중  : 09:00 ~ 12:00 무료
                        토요일 : 00:00 ~ 24:00 무료
                        일요일 : 04:00 ~ 24:00 무료

                        [요금 정보]
                        기본 주차 요금 정책 : 30분당 2000원
                        추가 주차 요금 정책 : 15분당 2000원
                        하루 최대 요금     : 50000원

                        주차 정보
                        주차 시작 시각 : 2024/2/16/금/21:00
                        주차 종료 시각 : 2024/2/18/월/05:00

                        계산 주차 금액
                        1. 2/16 유료 주차 시간 : 2024/2/16/21:00 ~ 2024/2/16/24:00 (180분)
                        1. 2/16 주차 금액 : 2000원 + 20000원 = 22000원

                        2. 2/17 유료 주차 시간 : 없음
                        2. 2/17 주차 금액 : 0원

                        3. 2/18 유료 주차 시간 : 2024/2/18/00:00 ~ 2024/2/18/04:00 (240분)
                        3. 2/18 주차 금액 : 2000원 + 28000원 = 30000원

                        4. 2/19 유료 주차 시간 : 2024/2/19/00:00 ~ 2024/2/19/05:01 (301분)
                        4. 2/19 주차 금액 : 2000원 + 38000원 = 40000원
                        주차 금액 합 : 22000원 + 0원 + 30000원 + 40000원 = 92000원
                 */
                Arguments.of(
                        new Parking(
                                new BaseInformation(),
                                new Location(),
                                new Space(),
                                new FreeOperatingTime(
                                        new TimeInfo(
                                                LocalTime.of(9, 0),
                                                LocalTime.of(12, 0)
                                        ),
                                        TimeInfo.ALL_DAY,
                                        new TimeInfo(
                                                LocalTime.of(4, 0),
                                                LocalTime.MAX
                                        )
                                ),
                                new OperatingTime(),
                                new FeePolicy(
                                        Fee.from(2000),
                                        Fee.from(2000),
                                        TimeUnit.from(30),
                                        TimeUnit.from(15),
                                        Fee.from(50000)
                                )
                        ),
                        LocalDateTime.of(2024, 2, 16, 21, 0),
                        LocalDateTime.of(2024, 2, 19, 5, 1),
                        Fee.from(92000)
                )
        );
    }
}
