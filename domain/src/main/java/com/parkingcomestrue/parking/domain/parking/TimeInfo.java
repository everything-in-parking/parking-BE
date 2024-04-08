package com.parkingcomestrue.parking.domain.parking;

import jakarta.persistence.Embeddable;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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

    public int calculateOverlapMinutes(LocalTime beginTime, LocalTime endTime) {
        if (this.endTime.isBefore(this.beginTime)) {
            TimeInfo today = new TimeInfo(this.beginTime, LocalTime.MAX);
            TimeInfo tomorrow = new TimeInfo(LocalTime.MIN, this.endTime);
            return today.calculateOverlapMinutes(beginTime, endTime) + tomorrow.calculateOverlapMinutes(beginTime,
                    endTime);
        }
        LocalTime overlapBeginTime = decideOverlapBeginTime(beginTime);
        LocalTime overlapEndTime = decideOverlapEndTime(endTime);
        int overlapMinutes = calculateBetweenMinutes(overlapBeginTime, overlapEndTime);
        return Math.max(0, overlapMinutes);
    }

    private LocalTime decideOverlapBeginTime(LocalTime beginTime) {
        if (beginTime.isAfter(this.beginTime)) {
            return beginTime;
        }
        return this.beginTime;
    }

    private LocalTime decideOverlapEndTime(LocalTime endTime) {
        if (endTime.isBefore(this.endTime)) {
            return endTime;
        }
        return this.endTime;
    }

    private int calculateBetweenMinutes(LocalTime beginTime, LocalTime endTime) {
        return calculateMinutes(endTime) - calculateMinutes(beginTime);
    }

    private int calculateMinutes(LocalTime localTime) {
        if (localTime.equals(LocalTime.MAX)) {
            return localTime.getHour() * 60 + localTime.getMinute() + 1;
        }
        return localTime.getHour() * 60 + localTime.getMinute();
    }
}
