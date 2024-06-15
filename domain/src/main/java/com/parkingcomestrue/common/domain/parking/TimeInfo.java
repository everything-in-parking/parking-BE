package com.parkingcomestrue.common.domain.parking;

import jakarta.persistence.Embeddable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
@EqualsAndHashCode(of = {"beginTime", "endTime"})
public class TimeInfo {

    public static final TimeInfo CLOSED = new TimeInfo(LocalTime.MIN, LocalTime.MIN);
    public static final LocalTime MAX_END_TIME = LocalTime.of(23, 59);
    public static final TimeInfo ALL_DAY = new TimeInfo(LocalTime.MIN, MAX_END_TIME);
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final String DELIMITER = "~";

    private LocalTime beginTime;
    private LocalTime endTime;

    public TimeInfo(LocalTime beginTime, LocalTime endTime) {
        if (beginTime == null || endTime == null) {
            this.beginTime = CLOSED.beginTime;
            this.endTime = CLOSED.endTime;
            return;
        }
        this.beginTime = beginTime;
        this.endTime = endTime;
    }

    public int calculateOverlapMinutes(LocalTime beginTime, LocalTime endTime) {
        if (this.endTime.isBefore(this.beginTime)) {
            TimeInfo today = new TimeInfo(this.beginTime, MAX_END_TIME);
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
        if (localTime.equals(MAX_END_TIME)) {
            return localTime.getHour() * 60 + localTime.getMinute() + 1;
        }
        return localTime.getHour() * 60 + localTime.getMinute();
    }

    @Override
    public String toString() {
        String beginTime = this.beginTime.format(TIME_FORMATTER);
        String endTime = this.endTime.format(TIME_FORMATTER);
        return beginTime + DELIMITER + endTime;
    }
}
