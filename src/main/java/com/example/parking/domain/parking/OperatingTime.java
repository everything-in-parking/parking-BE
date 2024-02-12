package com.example.parking.domain.parking;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class OperatingTime {

    public static final OperatingTime ALWAYS_OPEN = new OperatingTime(TimeInfo.ALL_DAY, TimeInfo.ALL_DAY, TimeInfo.ALL_DAY);

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
}
