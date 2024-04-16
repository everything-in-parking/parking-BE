package com.example.parking.domain.parking;

import java.time.DayOfWeek;

public enum Day {

    WEEKDAY,
    SATURDAY,
    HOLIDAY;

    public static Day from(DayOfWeek dayOfWeek) {
        if (dayOfWeek == DayOfWeek.SATURDAY) {
            return SATURDAY;
        }
        if (dayOfWeek == DayOfWeek.SUNDAY) {
            return HOLIDAY;
        }
        return WEEKDAY;
    }
}
