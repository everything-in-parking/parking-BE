package com.parkingcomestrue.common.domain.parking;

import static com.parkingcomestrue.common.domain.parking.TimeInfo.MAX_END_TIME;

import com.parkingcomestrue.common.infra.converter.TimeInfoConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
@Getter
public class FreeOperatingTime {

    public static final FreeOperatingTime ALWAYS_PAY = new FreeOperatingTime(TimeInfo.CLOSED, TimeInfo.CLOSED,
            TimeInfo.CLOSED);
    public static final FreeOperatingTime ALWAYS_FREE = new FreeOperatingTime(TimeInfo.ALL_DAY, TimeInfo.ALL_DAY,
            TimeInfo.ALL_DAY);

    @Convert(converter = TimeInfoConverter.class)
    private TimeInfo weekdayFreeOperatingTime;

    @Convert(converter = TimeInfoConverter.class)
    private TimeInfo saturdayFreeOperatingTime;

    @Convert(converter = TimeInfoConverter.class)
    private TimeInfo holidayFreeOperatingTime;

    public FreeOperatingTime(TimeInfo weekdayFreeOperatingTime, TimeInfo saturdayFreeOperatingTime, TimeInfo holidayFreeOperatingTime) {
        this.weekdayFreeOperatingTime = weekdayFreeOperatingTime;
        this.saturdayFreeOperatingTime = saturdayFreeOperatingTime;
        this.holidayFreeOperatingTime = holidayFreeOperatingTime;
    }

    public int calculateNonFreeUsageMinutes(DayParking dayParking) {
        TimeInfo today = getTodayTimeInfo(dayParking);
        if (isFreeDay(today)) {
            return 0;
        }
        LocalTime beginTime = dayParking.getBeginTime();
        LocalTime endTime = dayParking.getEndTime();
        int parkingMinutes = calculateMinutes(endTime) - calculateMinutes(beginTime);
        int overlapMinutes = today.calculateOverlapMinutes(beginTime, endTime);
        return parkingMinutes - overlapMinutes;
    }

    private TimeInfo getTodayTimeInfo(DayParking dayParking) {
        if (dayParking.isWeekDay()) {
            return weekdayFreeOperatingTime;
        }
        if (dayParking.isSaturday()) {
            return saturdayFreeOperatingTime;
        }
        return holidayFreeOperatingTime;
    }

    private boolean isFreeDay(TimeInfo today) {
        return today.equals(TimeInfo.ALL_DAY);
    }

    private int calculateMinutes(LocalTime localTime) {
        if (localTime.equals(MAX_END_TIME)) {
            return localTime.getHour() * 60 + localTime.getMinute() + 1;
        }
        return localTime.getHour() * 60 + localTime.getMinute();
    }
}
