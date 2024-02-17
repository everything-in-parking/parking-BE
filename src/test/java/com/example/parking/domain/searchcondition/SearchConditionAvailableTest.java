package com.example.parking.domain.searchcondition;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import com.example.parking.domain.parking.OperationType;
import com.example.parking.domain.parking.ParkingType;
import com.example.parking.domain.parking.PayType;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SearchConditionAvailableTest {

    @ParameterizedTest
    @MethodSource("parametersProvider")
    <E extends SearchConditionAvailable> void 설명과_enum_values_로_해당하는_값을_찾고_없으면_default를_반환한다(String description,
                                                                                             E expected, E... values) {
        //given, when
        E actual = SearchConditionAvailable.find(description, values);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> parametersProvider() {
        return Stream.of(
                arguments("민영 주차장", OperationType.PRIVATE, OperationType.values()),
                arguments("공영", OperationType.PUBLIC, OperationType.values()),
                arguments("노상", ParkingType.ON_STREET, ParkingType.values()),
                arguments("노외 주차장", ParkingType.OFF_STREET, ParkingType.values()),
                arguments("무료", FeeType.FREE, FeeType.values()),
                arguments("유료", FeeType.PAID, FeeType.values()),
                arguments("계좌", PayType.BANK_TRANSFER, PayType.values()),
                arguments("~", PayType.NO_INFO, PayType.values()),
                arguments("가격순", Priority.PRICE, Priority.values()),
                arguments("아무거나 입력", Priority.RECOMMENDATION, Priority.values())
        );
    }
}
