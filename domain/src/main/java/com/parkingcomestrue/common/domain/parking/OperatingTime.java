package com.parkingcomestrue.common.domain.parking;

import com.parkingcomestrue.common.infra.converter.TimeInfoConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Embeddable
public class OperatingTime {

    public static final OperatingTime ALWAYS_OPEN = new OperatingTime(TimeInfo.ALL_DAY, TimeInfo.ALL_DAY,
            TimeInfo.ALL_DAY);

    @Convert(converter = TimeInfoConverter.class)
    private TimeInfo weekdayOperatingTime;

    @Convert(converter = TimeInfoConverter.class)
    private TimeInfo saturdayOperatingTime;

    @Convert(converter = TimeInfoConverter.class)
    private TimeInfo holidayOperatingTime;

    public OperatingTime(TimeInfo weekdayOperatingTime,
                         TimeInfo saturdayOperatingTime,
                         TimeInfo holidayOperatingTime) {
        this.weekdayOperatingTime = weekdayOperatingTime;
        this.saturdayOperatingTime = saturdayOperatingTime;
        this.holidayOperatingTime = holidayOperatingTime;
    }

    public LocalTime getWeekdayBeginTime() {
        return weekdayOperatingTime.getBeginTime();
    }

    public LocalTime getWeekdayEndTime() {
        return weekdayOperatingTime.getEndTime();
    }

    public LocalTime getSaturdayBeginTime() {
        return saturdayOperatingTime.getBeginTime();
    }

    public LocalTime getSaturdayEndTime() {
        return saturdayOperatingTime.getEndTime();
    }

    public LocalTime getHolidayBeginTime() {
        return holidayOperatingTime.getBeginTime();
    }

    public LocalTime getHolidayEndTime() {
        return holidayOperatingTime.getEndTime();
    }
}
