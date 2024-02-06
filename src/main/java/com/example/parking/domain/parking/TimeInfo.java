package com.example.parking.domain.parking;

import jakarta.persistence.Embeddable;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class TimeInfo {

    public static final TimeInfo CLOSED = new TimeInfo(LocalTime.MIN, LocalTime.MIN);
    public static final TimeInfo ALL_DAY = new TimeInfo(LocalTime.MIN, LocalTime.MAX);

    private LocalTime beginTime;
    private LocalTime endTime;

    public TimeInfo(LocalTime beginTime, LocalTime endTime) {
        this.beginTime = beginTime;
        this.endTime = endTime;
    }
}
