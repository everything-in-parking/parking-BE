package com.example.parking.domain.parking;

import lombok.Getter;

@Getter
public class DayParking {

    private int minutes;
    private Day day;

    public DayParking(int minutes, Day day) {
        this.minutes = minutes;
        this.day = day;
    }
}
