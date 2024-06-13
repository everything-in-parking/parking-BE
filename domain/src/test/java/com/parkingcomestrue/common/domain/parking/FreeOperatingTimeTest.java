package com.parkingcomestrue.common.domain.parking;

import static com.parkingcomestrue.common.domain.parking.TimeInfo.MAX_END_TIME;

import com.parkingcomestrue.common.domain.parking.Day;
import com.parkingcomestrue.common.domain.parking.DayParking;
import com.parkingcomestrue.common.domain.parking.FreeOperatingTime;
import com.parkingcomestrue.common.domain.parking.TimeInfo;
import java.time.LocalTime;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class FreeOperatingTimeTest {

    @ParameterizedTest
    @MethodSource("getFreeOperatingTime")
    void 돈을_지불_해야_할_시간을_계산한다(FreeOperatingTime freeOperatingTime, DayParking dayParking, int expected) {
        // given, when, then
        Assertions.assertThat(freeOperatingTime.calculateNonFreeUsageMinutes(dayParking))
                .isEqualTo(expected);
    }

    static Stream<Arguments> getFreeOperatingTime() {
        return Stream.of(
                /*
                        주중 무료 시간  : 00:00 ~ 24:00
                        토요일 무료 시간 : 00:00 ~ 24:00
                        공휴일 무료 시간 : 00:00 ~ 24:00

                        주차 요일 : 주중
                        주차 시간 : 00:00 ~ 24:00
                        주차 시간(분) : 1440분
                        무료 시간(분) : 1440분
                        유료 시간(분) : 0분
                 */
                Arguments.of(
                        FreeOperatingTime.ALWAYS_FREE,
                        new DayParking(Day.WEEKDAY, LocalTime.MIN, MAX_END_TIME),
                        0
                ),
                /*
                        주중 무료 시간  : 09:00 ~ 13:00
                        토요일 무료 시간 : 00:00 ~ 24:00
                        공휴일 무료 시간 : 00:00 ~ 24:00

                        주차 요일 : 주중
                        주차 시간 : 00:00 ~ 24:00
                        주차 시간(분) : 1440분
                        무료 시간(분) : 240분
                        유료 시간(분) : 1200분
                 */
                Arguments.of(
                        new FreeOperatingTime(
                                new TimeInfo(
                                        LocalTime.of(9, 0), LocalTime.of(13, 0)
                                ),
                                TimeInfo.ALL_DAY,
                                TimeInfo.ALL_DAY
                        ),
                        new DayParking(Day.WEEKDAY, LocalTime.MIN, MAX_END_TIME),
                        1200
                ),
                /*
                        주중 무료 시간  : 09:00 ~ 13:00
                        토요일 무료 시간 : 00:00 ~ 24:00
                        공휴일 무료 시간 : 00:00 ~ 24:00

                        주차 요일 : 주중
                        주차 시간 : 09:00 ~ 13:00
                        주차 시간(분) : 240분
                        무료 시간(분) : 240분
                        유료 시간(분) : 0분
                 */
                Arguments.of(
                        new FreeOperatingTime(
                                new TimeInfo(
                                        LocalTime.of(9, 0), LocalTime.of(13, 0)
                                ),
                                TimeInfo.ALL_DAY,
                                TimeInfo.ALL_DAY
                        ),
                        new DayParking(Day.WEEKDAY, LocalTime.of(9, 0), LocalTime.of(13, 0)),
                        0
                ),
                /*
                        주중 무료 시간  : 09:00 ~ 13:00
                        토요일 무료 시간 : 00:00 ~ 24:00
                        공휴일 무료 시간 : 00:00 ~ 24:00

                        주차 요일 : 주중
                        주차 시간 : 08:00 ~ 12:00
                        주차 시간(분) : 240분
                        무료 시간(분) : 180분
                        유료 시간(분) : 60분
                 */
                Arguments.of(
                        new FreeOperatingTime(
                                new TimeInfo(
                                        LocalTime.of(9, 0), LocalTime.of(13, 0)
                                ),
                                TimeInfo.ALL_DAY,
                                TimeInfo.ALL_DAY
                        ),
                        new DayParking(Day.WEEKDAY, LocalTime.of(8, 0), LocalTime.of(12, 0)),
                        60
                ),
                /*
                        주중 무료 시간  : 09:00 ~ 13:00
                        토요일 무료 시간 : 00:00 ~ 24:00
                        공휴일 무료 시간 : 00:00 ~ 24:00

                        주차 요일 : 주중
                        주차 시간 : 10:20 ~ 12:50
                        주차 시간(분) : 150분
                        무료 시간(분) : 150분
                        유료 시간(분) : 0분
                 */
                Arguments.of(
                        new FreeOperatingTime(
                                new TimeInfo(
                                        LocalTime.of(9, 0), LocalTime.of(13, 0)
                                ),
                                TimeInfo.ALL_DAY,
                                TimeInfo.ALL_DAY
                        ),
                        new DayParking(Day.WEEKDAY, LocalTime.of(10, 20), LocalTime.of(12, 50)),
                        0
                ),
                /*
                        주중 무료 시간  : 09:00 ~ 13:00
                        토요일 무료 시간 : 00:00 ~ 24:00
                        공휴일 무료 시간 : 00:00 ~ 24:00

                        주차 요일 : 주중
                        주차 시간 : 13:20 ~ 14:45
                        주차 시간(분) : 85분
                        무료 시간(분) : 0분
                        유료 시간(분) : 85분
                 */
                Arguments.of(
                        new FreeOperatingTime(
                                new TimeInfo(
                                        LocalTime.of(9, 0), LocalTime.of(13, 0)
                                ),
                                TimeInfo.ALL_DAY,
                                TimeInfo.ALL_DAY
                        ),
                        new DayParking(Day.WEEKDAY, LocalTime.of(13, 20), LocalTime.of(14, 45)),
                        85
                ),
                /*
                        주중 무료 시간  : 09:00 ~ 13:00
                        토요일 무료 시간 : 00:00 ~ 24:00
                        공휴일 무료 시간 : 00:00 ~ 24:00

                        주차 요일 : 주중
                        주차 시간 : 11:20 ~ 19:00
                        주차 시간(분) : 460분
                        무료 시간(분) : 100분
                        유료 시간(분) : 360분
                 */
                Arguments.of(
                        new FreeOperatingTime(
                                new TimeInfo(
                                        LocalTime.of(9, 0), LocalTime.of(13, 0)
                                ),
                                TimeInfo.ALL_DAY,
                                TimeInfo.ALL_DAY
                        ),
                        new DayParking(Day.WEEKDAY, LocalTime.of(11, 20), LocalTime.of(19, 0)),
                        360
                ),
                /*
                        주중 무료 시간  : 09:00 ~ 13:00
                        토요일 무료 시간 : 00:00 ~ 24:00
                        공휴일 무료 시간 : 00:00 ~ 24:00

                        주차 요일 : 토요일
                        주차 시간 : 11:20 ~ 19:00
                        주차 시간(분) : 460분
                        무료 시간(분) : 460분
                        유료 시간(분) : 0분
                 */
                Arguments.of(
                        new FreeOperatingTime(
                                new TimeInfo(
                                        LocalTime.of(9, 0), LocalTime.of(13, 0)
                                ),
                                TimeInfo.ALL_DAY,
                                TimeInfo.ALL_DAY
                        ),
                        new DayParking(Day.SATURDAY, LocalTime.of(11, 20), LocalTime.of(19, 0)),
                        0
                )
        );
    }
}
