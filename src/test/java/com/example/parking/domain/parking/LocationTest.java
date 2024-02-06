package com.example.parking.domain.parking;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class LocationTest {

    @CsvSource({"0.1, -", " , 0.2", "nil, nil"})
    @ParameterizedTest
    void 이상한_값이_들어오면_특정_음수_좌표를_반환한다(String longitude, String latitude) {
        //given, when
        Location actual = Location.of(longitude, latitude);
        Location expected = Location.of("-1.0", "-1.0");

        //then
        assertThat(actual).isEqualTo(expected);
    }
}
