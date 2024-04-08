package com.parkingcomestrue.parking.domain.parking;

import java.time.LocalTime;
import lombok.Getter;

@Getter
public class DayParking {

    private final Day day;
    private final LocalTime beginTime;
    private final LocalTime endTime;

    public DayParking(Day day, LocalTime beginTime, LocalTime endTime) {
        this.day = day;
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public boolean isWeekDay() {
        return day == Day.WEEKDAY;
    }

    public boolean isSaturday() {
        return day == Day.SATURDAY;
    }

    public boolean isHoliday() {
        return day == Day.HOLIDAY;
    }
}
