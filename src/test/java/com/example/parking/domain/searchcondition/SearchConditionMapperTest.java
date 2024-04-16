package com.example.parking.domain.searchcondition;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import com.example.parking.application.SearchConditionMapper;
import com.example.parking.domain.parking.OperationType;
import com.example.parking.domain.parking.ParkingType;
import com.example.parking.domain.parking.PayType;
import com.example.parking.support.exception.ClientException;
import com.example.parking.support.exception.ExceptionInformation;
import java.util.List;
import java.util.stream.Stream;
import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class SearchConditionMapperTest {

    private static final SearchConditionMapper searchConditionMapper = new SearchConditionMapper();

    @ParameterizedTest
    @MethodSource("parametersProvider1")
    <E extends Enum<E> & SearchConditionAvailable> void enum_class와_설명으로_해당하는_값을_반환한다(Class<E> clazz,
                                                                                      String description,
                                                                                      E expected) {
        //given, when
        E actual = searchConditionMapper.toEnum(clazz, description);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> parametersProvider1() {
        return Stream.of(
                arguments(OperationType.class, "민영 주차장", OperationType.PRIVATE),
                arguments(OperationType.class, "공영", OperationType.PUBLIC),
                arguments(ParkingType.class, "노상", ParkingType.ON_STREET),
                arguments(ParkingType.class, "노외 주차장", ParkingType.OFF_STREET),
                arguments(FeeType.class, "무료", FeeType.FREE),
                arguments(FeeType.class, "유료", FeeType.PAID),
                arguments(PayType.class, "계좌", PayType.BANK_TRANSFER),
                arguments(Priority.class, "가격순", Priority.PRICE)
        );
    }

    @Test
    void 변환시_해당하는_값이_없으면_클라이언트_예외를_반환한다() {
        //given, when, then
        assertThatThrownBy(() -> searchConditionMapper.toEnum(PayType.class, "아무거나 입력"))
                .isInstanceOf(ClientException.class)
                .hasMessage(ExceptionInformation.INVALID_DESCRIPTION.getMessage());
    }

    @ParameterizedTest
    @MethodSource("parametersProvider2")
    <E extends Enum<E> & SearchConditionAvailable> void enum_class와_설명들로_해당하는_값들을_반환한다(Class<E> clazz,
                                                                                       List<String> descriptions,
                                                                                       List<E> expected) {
        //given, when
        List<E> actual = searchConditionMapper.toEnums(clazz, descriptions);

        //then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    static Stream<Arguments> parametersProvider2() {
        return Stream.of(
                arguments(OperationType.class, List.of("민영 주차장", "공영 주차장"),
                        List.of(OperationType.PRIVATE, OperationType.PUBLIC)),
                arguments(OperationType.class, List.of("공영"), List.of(OperationType.PUBLIC)),
                arguments(ParkingType.class, List.of("노상", "기계"),
                        List.of(ParkingType.ON_STREET, ParkingType.MECHANICAL)),
                arguments(ParkingType.class, List.of("노외 주차장"), List.of(ParkingType.OFF_STREET)),
                arguments(FeeType.class, List.of("무료", "유료"), List.of(FeeType.FREE, FeeType.PAID)),
                arguments(FeeType.class, List.of("유료"), List.of(FeeType.PAID)),
                arguments(PayType.class, List.of("계좌", "현금"), List.of(PayType.BANK_TRANSFER, PayType.CASH)),
                arguments(Priority.class, List.of("가격순"), List.of(Priority.PRICE))
        );
    }

    @ParameterizedTest
    @MethodSource("parametersProvider3")
    <E extends Enum<E> & SearchConditionAvailable> void 해당하는_enum_class의_기본_값을_제외한_값들을_가져온다(Class<E> clazz,
                                                                                            List<String> expected) {
        //given, when
        List<String> actual = searchConditionMapper.getValues(clazz);

        //then
        assertThat(actual).containsExactlyInAnyOrderElementsOf(expected);
    }

    static Stream<Arguments> parametersProvider3() {
        return Stream.of(
                arguments(OperationType.class, List.of("공영", "민영")),
                arguments(ParkingType.class, List.of("노상", "기계", "노외")),
                arguments(FeeType.class, List.of("무료", "유료")),
                arguments(PayType.class, List.of("카드", "계좌", "현금")),
                arguments(Priority.class, List.of("가까운순", "가격순"))
        );
    }
}
