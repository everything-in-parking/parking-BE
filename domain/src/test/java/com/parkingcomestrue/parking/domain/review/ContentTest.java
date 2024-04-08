package com.parkingcomestrue.parking.domain.review;

import static com.parkingcomestrue.parking.support.exception.ExceptionInformation.INVALID_CONTENT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import com.parkingcomestrue.parking.support.exception.DomainException;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

class ContentTest {

    @ParameterizedTest
    @MethodSource("parametersProvider")
    void 설명에_Content를_반환한다(String description, Content expected) {
        //given, when
        Content actual = Content.find(description);

        //then
        assertThat(actual).isEqualTo(expected);
    }

    static Stream<Arguments> parametersProvider() {
        return Stream.of(
                arguments("결제가 편리해요", Content.EASY_TO_PAY),
                arguments("직원", Content.FRIENDLY_STAFF)
        );
    }

    @Test
    void 설명에_맞는_Content가_없으면_예외처리한다() {
        //given, when, then
        assertThatThrownBy(() -> Content.find("없는 설명"))
                .isInstanceOf(DomainException.class)
                .hasMessage(INVALID_CONTENT.getMessage());
    }
}
