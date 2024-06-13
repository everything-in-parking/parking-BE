package com.parkingcomestrue.common.infra.converter;

import com.parkingcomestrue.common.domain.parking.TimeInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class TimeInfoConverterTest {

    private TimeInfoConverter converter = new TimeInfoConverter();

    @Test
    void TimeInfo에서_컬럼으로_변경() {
        //given
        TimeInfo allDay = TimeInfo.ALL_DAY;
        String expected = "00:00~23:59";

        //when
        String actual = converter.convertToDatabaseColumn(allDay);

        //then
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 컬럼에서_TimeInfo로_변경() {
        //given
        String column = "00:00~23:59";
        TimeInfo expected = TimeInfo.ALL_DAY;

        //when
        TimeInfo actual = converter.convertToEntityAttribute(column);

        //then
        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
