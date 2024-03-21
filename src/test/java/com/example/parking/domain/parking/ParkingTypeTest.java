package com.example.parking.domain.parking;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.List;
import java.util.stream.Stream;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayNameGeneration(ReplaceUnderscores.class)
class ParkingTypeTest {

    @Test
    void description에_맞는_ParkingType_리스트를_반환() {
        List<String> descriptions = List.of("노상");

        List<ParkingType> parkingTypes = ParkingType.collectMatch(descriptions);
        assertAll(
                () -> Assertions.assertThat(parkingTypes).hasSize(1),
                () -> Assertions.assertThat(parkingTypes.get(0)).isEqualTo(ParkingType.ON_STREET)
        );
    }

    @Test
    void description에_맞는_ParkingType이_없으변_빈_리스트를_반환() {
        List<String> descriptions = List.of("야외");

        List<ParkingType> parkingTypes = ParkingType.collectMatch(descriptions);
        Assertions.assertThat(parkingTypes).hasSize(0);
    }

    @ParameterizedTest(name = "찾으려는 주차장 정보: {0} | 예상 결과: {1}")
    @MethodSource("parametersProvider")
    void 주차장_유형_설명으로_조회(String description, ParkingType expected) {
        //given, when
        ParkingType actual = ParkingType.find(description);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> parametersProvider() {
        return Stream.of(
                arguments("노외", ParkingType.OFF_STREET),
                arguments("노외주차장", ParkingType.OFF_STREET),
                arguments("노외 주차장", ParkingType.OFF_STREET),
                arguments("노상", ParkingType.ON_STREET),
                arguments("노상주차장", ParkingType.ON_STREET),
                arguments("노상 주차장", ParkingType.ON_STREET),
                arguments("기계", ParkingType.MECHANICAL),
                arguments("기계식주차", ParkingType.MECHANICAL),
                arguments("기계식 주차장", ParkingType.MECHANICAL),
                arguments("-", ParkingType.NO_INFO),
                arguments("", ParkingType.NO_INFO),
                arguments("차영호 주차장", ParkingType.NO_INFO)
        );
    }
}
