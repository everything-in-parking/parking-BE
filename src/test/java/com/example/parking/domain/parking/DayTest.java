package com.example.parking.domain.parking;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.DayOfWeek;
import java.util.stream.Stream;

class DayTest {

    @ParameterizedTest
    @MethodSource("getDayOfWeek")
    void 요일을_주중_토요일_공휴일로_나눈다(DayOfWeek dayOfWeek, Day expected) {
        // given, when, then
        Assertions.assertThat(Day.from(dayOfWeek))
                .isSameAs(expected);
    }

    static Stream<Arguments> getDayOfWeek() {
        return Stream.of(
                Arguments.of(
                        DayOfWeek.MONDAY,
                        Day.WEEKDAY
                ),
                Arguments.of(
                        DayOfWeek.TUESDAY,
                        Day.WEEKDAY
                ),
                Arguments.of(
                        DayOfWeek.WEDNESDAY,
                        Day.WEEKDAY
                ),
                Arguments.of(
                        DayOfWeek.THURSDAY,
                        Day.WEEKDAY
                ),
                Arguments.of(
                        DayOfWeek.FRIDAY,
                        Day.WEEKDAY
                ),
                Arguments.of(
                        DayOfWeek.SATURDAY,
                        Day.SATURDAY
                ),
                Arguments.of(
                        DayOfWeek.SUNDAY,
                        Day.HOLIDAY
                )
        );
    }
}
