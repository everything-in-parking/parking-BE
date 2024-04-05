package com.parkingcomestrue.parking.domain;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
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

    @AttributeOverride(name = "beginTime", column = @Column(name = "weekday_begin_time"))
    @AttributeOverride(name = "endTime", column = @Column(name = "weekday_end_time"))
    @Embedded
    private TimeInfo weekday;

    @AttributeOverride(name = "beginTime", column = @Column(name = "saturday_begin_time"))
    @AttributeOverride(name = "endTime", column = @Column(name = "saturday_end_time"))
    @Embedded
    private TimeInfo saturday;

    @AttributeOverride(name = "beginTime", column = @Column(name = "holiday_begin_time"))
    @AttributeOverride(name = "endTime", column = @Column(name = "holiday_end_time"))
    @Embedded
    private TimeInfo holiday;

    public OperatingTime(TimeInfo weekday,
                         TimeInfo saturday,
                         TimeInfo holiday) {
        this.weekday = weekday;
        this.saturday = saturday;
        this.holiday = holiday;
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
