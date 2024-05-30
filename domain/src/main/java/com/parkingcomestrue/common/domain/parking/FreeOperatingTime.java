package com.parkingcomestrue.common.domain.parking;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class FreeOperatingTime {

    public static final FreeOperatingTime ALWAYS_PAY = new FreeOperatingTime(TimeInfo.CLOSED, TimeInfo.CLOSED,
            TimeInfo.CLOSED);
    public static final FreeOperatingTime ALWAYS_FREE = new FreeOperatingTime(TimeInfo.ALL_DAY, TimeInfo.ALL_DAY,
            TimeInfo.ALL_DAY);

    @AttributeOverride(name = "beginTime", column = @Column(name = "weekday_free_begin_time"))
    @AttributeOverride(name = "endTime", column = @Column(name = "weekday_free_end_time"))
    @Embedded
    private TimeInfo weekday;

    @AttributeOverride(name = "beginTime", column = @Column(name = "saturday_free_begin_time"))
    @AttributeOverride(name = "endTime", column = @Column(name = "saturday_free_end_time"))
    @Embedded
    private TimeInfo saturday;

    @AttributeOverride(name = "beginTime", column = @Column(name = "holiday_free_begin_time"))
    @AttributeOverride(name = "endTime", column = @Column(name = "holiday_free_end_time"))
    @Embedded
    private TimeInfo holiday;

    public FreeOperatingTime(TimeInfo weekday, TimeInfo saturday, TimeInfo holiday) {
        this.weekday = weekday;
        this.saturday = saturday;
        this.holiday = holiday;
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
            return weekday;
        }
        if (dayParking.isSaturday()) {
            return saturday;
        }
        return holiday;
    }

    private boolean isFreeDay(TimeInfo today) {
        return today.equals(TimeInfo.ALL_DAY);
    }

    private int calculateMinutes(LocalTime localTime) {
        if (localTime.equals(LocalTime.MAX)) {
            return localTime.getHour() * 60 + localTime.getMinute() + 1;
        }
        return localTime.getHour() * 60 + localTime.getMinute();
    }

    public LocalTime getWeekdayBeginTime() {
        return weekday.getBeginTime();
    }

    public LocalTime getWeekdayEndTime() {
        return weekday.getEndTime();
    }

    public LocalTime getSaturdayBeginTime() {
        return saturday.getBeginTime();
    }

    public LocalTime getSaturdayEndTime() {
        return saturday.getEndTime();
    }

    public LocalTime getHolidayBeginTime() {
        return holiday.getBeginTime();
    }

    public LocalTime getHolidayEndTime() {
        return holiday.getEndTime();
    }
}
