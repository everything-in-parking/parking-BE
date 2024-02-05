package com.example.parking.domain.parking;

import jakarta.persistence.Embeddable;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class TimeInfo {

    private LocalTime beginTime;
    private LocalTime endTime;

    public TimeInfo(LocalTime beginTime, LocalTime endTime) {
        this.beginTime = beginTime;
        this.endTime = endTime;
    }
}
