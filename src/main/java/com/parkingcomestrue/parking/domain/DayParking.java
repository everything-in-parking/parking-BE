package com.parkingcomestrue.parking.domain;

import lombok.Getter;

import java.time.LocalTime;

@Getter
public class DayParking {

    private Day day;
    private LocalTime beginTime;
    private LocalTime endTime;

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
