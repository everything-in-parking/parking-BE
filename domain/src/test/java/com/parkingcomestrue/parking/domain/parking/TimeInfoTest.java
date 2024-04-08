package com.parkingcomestrue.parking.domain.parking;

import java.time.LocalTime;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class TimeInfoTest {

    @ParameterizedTest
    @MethodSource("getTimeInfos")
    void 겹치는_시간이_몇_분인지_계산한다(TimeInfo timeInfo, LocalTime beginTime, LocalTime endTime, int expected) {
        // given, when, then
        Assertions.assertThat(timeInfo.calculateOverlapMinutes(beginTime, endTime))
                .isEqualTo(expected);
    }

    static Stream<Arguments> getTimeInfos() {
        return Stream.of(
                /*
                        무료 운영 시간 : 00:00 ~ 24:00
                        주차 시간     : 00:00 ~ 24:00
                        겹침 시간     : 00:00 ~ 24:00 -> 1440분
                 */
                Arguments.of(
                        new TimeInfo(LocalTime.MIN, LocalTime.MAX),
                        LocalTime.MIN,
                        LocalTime.MAX,
                        1440),
                /*
                        무료 운영 시간 : 00:00 ~ 24:00
                        주차 시간     : 01:30 ~ 22:11
                        겹침 시간     : 01:30 ~ 22:11 -> 1241분
                 */
                Arguments.of(
                        new TimeInfo(LocalTime.MIN, LocalTime.MAX),
                        LocalTime.of(1, 30),
                        LocalTime.of(22, 11),
                        1241),
                /*
                        무료 운영 시간 : 09:00 ~ 21:00
                        주차 시간     : 08:00 ~ 09:00
                        겹침 시간     : 겹치지 않는다 -> 0분
                 */
                Arguments.of(
                        new TimeInfo(LocalTime.of(9, 0), LocalTime.of(21, 0)),
                        LocalTime.of(8, 0),
                        LocalTime.of(9, 0),
                        0),
                /*
                        무료 운영 시간 : 09:00 ~ 21:00
                        주차 시간     : 08:00 ~ 09:30
                        겹침 시간     : 09:00 ~ 09:30 -> 30분
                 */
                Arguments.of(
                        new TimeInfo(LocalTime.of(9, 0), LocalTime.of(21, 0)),
                        LocalTime.of(8, 0),
                        LocalTime.of(9, 30),
                        30),
                /*
                        무료 운영 시간 : 09:00 ~ 21:00
                        주차 시간     : 08:00 ~ 21:00
                        겹침 시간     : 09:00 ~ 21:00 -> 720분
                 */
                Arguments.of(
                        new TimeInfo(LocalTime.of(9, 0), LocalTime.of(21, 0)),
                        LocalTime.of(8, 0),
                        LocalTime.of(21, 0),
                        720),
                /*
                        무료 운영 시간 : 09:00 ~ 21:00
                        주차 시간     : 08:00 ~ 22:40
                        겹침 시간     : 00:00 ~ 24:00 -> 1440분
                 */
                Arguments.of(
                        new TimeInfo(LocalTime.of(9, 0), LocalTime.of(21, 0)),
                        LocalTime.of(8, 0),
                        LocalTime.of(22, 40),
                        720),
                /*
                        무료 운영 시간 : 09:00 ~ 21:00
                        주차 시간     : 10:00 ~ 17:20
                        겹침 시간     : 10:00 ~ 17:20 -> 440분
                 */
                Arguments.of(
                        new TimeInfo(LocalTime.of(9, 0), LocalTime.of(21, 0)),
                        LocalTime.of(10, 0),
                        LocalTime.of(17, 20),
                        440),
                /*
                        무료 운영 시간 : 09:00 ~ 21:00
                        주차 시간     : 10:00 ~ 21:00
                        겹침 시간     : 10:00 ~ 21:00 -> 660분
                 */
                Arguments.of(
                        new TimeInfo(LocalTime.of(9, 0), LocalTime.of(21, 0)),
                        LocalTime.of(10, 0),
                        LocalTime.of(21, 0),
                        660),
                /*
                        무료 운영 시간 : 09:00 ~ 21:00
                        주차 시간     : 21:00 ~ 23:00
                        겹침 시간     : 겹치지 않는다 -> 0분
                 */
                Arguments.of(
                        new TimeInfo(LocalTime.of(9, 0), LocalTime.of(21, 0)),
                        LocalTime.of(21, 0),
                        LocalTime.of(23, 0),
                        0),
                /*
                        무료 운영 시간 : 21:00 ~ 09:00
                        주차 시간     : 21:00 ~ 24:00
                        겹침 시간     : 21:00 ~ 24:00 -> 180분
                 */
                Arguments.of(
                        new TimeInfo(LocalTime.of(21, 0), LocalTime.of(9, 0)),
                        LocalTime.of(21, 0),
                        LocalTime.MAX,
                        180),
                /*
                        무료 운영 시간 : 21:00 ~ 09:00
                        주차 시간     : 09:00 ~ 21:00
                        겹침 시간     : 겹치지 않는다 -> 0분
                 */
                Arguments.of(
                        new TimeInfo(LocalTime.of(21, 0), LocalTime.of(9, 0)),
                        LocalTime.of(9, 0),
                        LocalTime.of(21, 0),
                        0),
                /*
                        무료 운영 시간 : 21:00 ~ 09:00
                        주차 시간     : 09:00 ~ 21:01
                        겹침 시간     : 21:00 ~ 21:01 -> 1분
                 */
                Arguments.of(
                        new TimeInfo(LocalTime.of(21, 0), LocalTime.of(9, 0)),
                        LocalTime.of(9, 0),
                        LocalTime.of(21, 1),
                        1),
                /*
                        무료 운영 시간 : 21:00 ~ 09:00
                        주차 시간     : 08:59 ~ 21:00
                        겹침 시간     : 08:59 ~ 09:00 -> 1분
                 */
                Arguments.of(
                        new TimeInfo(LocalTime.of(21, 0), LocalTime.of(9, 0)),
                        LocalTime.of(8, 59),
                        LocalTime.of(21, 0),
                        1),
                /*
                        무료 운영 시간 : 21:00 ~ 09:00
                        주차 시간     : 08:59 ~ 21:01
                        겹침 시간     : 08:59 ~ 09:00 + 21:00 ~ 21:01 -> 2분
                 */
                Arguments.of(
                        new TimeInfo(LocalTime.of(21, 0), LocalTime.of(9, 0)),
                        LocalTime.of(8, 59),
                        LocalTime.of(21, 1),
                        2)
        );
    }
}
