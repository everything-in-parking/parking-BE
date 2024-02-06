package com.example.parking.domain.parking;

import static com.example.parking.domain.parking.PayType.BANK_TRANSFER;
import static com.example.parking.domain.parking.PayType.CARD;
import static com.example.parking.domain.parking.PayType.CASH;
import static com.example.parking.domain.parking.PayType.NO_INFO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PayTypesTest {

    @ParameterizedTest
    @MethodSource("parametersProvider")
    void 열거형으로_결제방식을_생성한다(Collection<PayType> payTypes, String expected) {
        //given, when
        PayTypes actual = PayTypes.from(payTypes);

        //then
        assertThat(actual.getDescription()).isEqualTo(expected);
    }

    static Stream<Arguments> parametersProvider() {
        final String DELIMITER = ", ";
        return Stream.of(
                arguments(Set.of(CARD), CARD.getDescription()),
                arguments(Set.of(CARD, CASH), CARD.getDescription() + DELIMITER + CASH.getDescription()),
                arguments(Set.of(CARD, CASH, BANK_TRANSFER),
                        BANK_TRANSFER.getDescription() + DELIMITER + CARD.getDescription() + DELIMITER
                                + CASH.getDescription()),
                arguments(Set.of(CARD, CASH, BANK_TRANSFER, NO_INFO), NO_INFO.getDescription())
        );
    }
}


