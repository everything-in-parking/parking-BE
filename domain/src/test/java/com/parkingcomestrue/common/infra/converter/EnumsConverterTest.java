package com.parkingcomestrue.common.infra.converter;

import static org.assertj.core.api.Assertions.assertThat;

import com.parkingcomestrue.common.domain.searchcondition.FeeType;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EnumsConverterTest {

    @DisplayName("DB에 Enum Set을 저장할 때, Enum의 값 이름과 " + '"' + ", " + '"' + " 구분자를 이용해서 저장된다.")
    @Test
    void convertTest() {
        //given
        FeeTypeConverter feeTypeConverter = new FeeTypeConverter();
        Set<FeeType> expectedFeeType = Set.of(FeeType.FREE, FeeType.PAID);
        String expectedDatabaseColumn = expectedFeeType.stream().map(FeeType::name)
                .sorted()
                .collect(Collectors.joining(", "));

        //when
        String actualDatabaseField = feeTypeConverter.convertToDatabaseColumn(expectedFeeType);
        Set<FeeType> actualFeeTypes = feeTypeConverter.convertToEntityAttribute(actualDatabaseField);

        //then
        assertThat(actualDatabaseField).isEqualTo(expectedDatabaseColumn);
        assertThat(actualFeeTypes).isEqualTo(expectedFeeType);
    }
}
