//package com.example.parking.domain.parking;
//
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.params.ParameterizedTest;
//import org.junit.jupiter.params.provider.Arguments;
//import org.junit.jupiter.params.provider.MethodSource;
//
//import java.util.List;
//import java.util.stream.Stream;
//
//class ParkingTest {
//
//    @ParameterizedTest
//    @MethodSource("getParking")
//    void 주어진_주차장_정보에_따라_요금_계산을_한다(Parking parking, List<DayParking> dayParkings, Fee expected) {
//        // given, when
//        Fee actual = parking.calculateParkingFee(dayParkings);
//
//        // then
//        Assertions.assertThat(actual).isEqualTo(expected);
//    }
//
//    static Stream<Arguments> getParking() {
//        return Stream.of(
//                Arguments.of(
//                        new Parking
//                                (
//                                        new BaseInformation(),
//                                        Type.ON_STREET,
//                                        new Location(),
//                                        new Space(),
//                                        new OperatingTime(),
//                                        new FeePolicy(
//                                                Fee.from(2000),
//                                                Fee.from(2000),
//                                                TimeUnit.from(15),
//                                                TimeUnit.from(15),
//                                                Fee.from(10000)),
//                                        new FreePolicy(true, true, true)
//                                ),
//                        List.of(
//                                new DayParking(15, Day.WEEKDAY),
//                                new DayParking(240, Day.SATURDAY)
//                        ),
//                        Fee.ZERO
//                ),
//                Arguments.of(
//                        new Parking
//                                (
//                                        new BaseInformation(),
//                                        Type.ON_STREET,
//                                        new Location(),
//                                        new Space(),
//                                        new OperatingTime(),
//                                        new FeePolicy(
//                                                Fee.from(2000),
//                                                Fee.from(2000),
//                                                TimeUnit.from(15),
//                                                TimeUnit.from(15),
//                                                Fee.from(10000)),
//                                        new FreePolicy(false, false, true)
//                                ),
//                        List.of(
//                                new DayParking(15, Day.WEEKDAY),
//                                new DayParking(240, Day.SATURDAY)
//                        ),
//                        Fee.from(2000 + 10000)
//                ),
//                Arguments.of(
//                        new Parking
//                                (
//                                        new BaseInformation(),
//                                        Type.ON_STREET,
//                                        new Location(),
//                                        new Space(),
//                                        new OperatingTime(),
//                                        new FeePolicy(
//                                                Fee.from(2000),
//                                                Fee.from(2000),
//                                                TimeUnit.from(15),
//                                                TimeUnit.from(15),
//                                                Fee.from(200000)),
//                                        new FreePolicy(false, false, true)
//                                ),
//                        List.of(
//                                new DayParking(15, Day.WEEKDAY),
//                                new DayParking(240, Day.SATURDAY)
//                        ),
//                        Fee.from(2000 + 32000)
//                ),
//                Arguments.of(
//                        new Parking
//                                (
//                                        new BaseInformation(),
//                                        Type.ON_STREET,
//                                        new Location(),
//                                        new Space(),
//                                        new OperatingTime(),
//                                        new FeePolicy(
//                                                Fee.from(2000),
//                                                Fee.from(2000),
//                                                TimeUnit.from(15),
//                                                TimeUnit.from(15),
//                                                Fee.from(200000)),
//                                        new FreePolicy(false, false, true)
//                                ),
//                        List.of(
//                                new DayParking(15, Day.WEEKDAY),
//                                new DayParking(241, Day.SATURDAY)
//                        ),
//                        Fee.from(2000 + 34000)
//                ),
//                Arguments.of(
//                        new Parking
//                                (
//                                        new BaseInformation(),
//                                        Type.ON_STREET,
//                                        new Location(),
//                                        new Space(),
//                                        new OperatingTime(),
//                                        new FeePolicy(
//                                                Fee.from(2000),
//                                                Fee.from(2000),
//                                                TimeUnit.from(15),
//                                                TimeUnit.from(15),
//                                                Fee.from(200000)),
//                                        new FreePolicy(false, false, true)
//                                ),
//                        List.of(
//                                new DayParking(15, Day.WEEKDAY),
//                                new DayParking(241, Day.SATURDAY),
//                                new DayParking(240, Day.HOLIDAY)
//                        ),
//                        Fee.from(2000 + 34000)
//                ),
//                Arguments.of(
//                        new Parking
//                                (
//                                        new BaseInformation(),
//                                        Type.ON_STREET,
//                                        new Location(),
//                                        new Space(),
//                                        new OperatingTime(),
//                                        new FeePolicy(
//                                                Fee.from(2000),
//                                                Fee.from(3000),
//                                                TimeUnit.from(15),
//                                                TimeUnit.from(15),
//                                                Fee.from(200000)),
//                                        new FreePolicy(false, false, true)
//                                ),
//                        List.of(
//                                new DayParking(181, Day.WEEKDAY)
//                        ),
//                        Fee.from(2000 + 36000)
//                )
//        );
//    }
//}
