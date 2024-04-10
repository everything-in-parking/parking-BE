package com.parkingcomestrue.parking.application.parking.dto;

import com.parkingcomestrue.parking.application.review.dto.ReviewInfoResponse;
import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParkingDetailInfoResponse {

    private String parkingName;
    private String parkingType;
    private Double latitude;
    private Double longitude;
    private FeeInfo feeInfo;
    private Integer currentParkingCount;
    private Integer capacity;
    private Integer lastUpdated;
    private String tel;
    private String paymentType;
    private WeekdayOperatingTime weekdayOperatingTime;
    private SaturdayOperatingTime saturdayOperatingTime;
    private HolidayOperatingTime holidayOperatingTime;
    private ReviewInfoResponse reviewInfo;

    public ParkingDetailInfoResponse(String parkingName, String parkingType, Double latitude, Double longitude,
                                     FeeInfo feeInfo,
                                     Integer currentParkingCount, Integer capacity, Integer lastUpdated, String tel,
                                     String paymentType, WeekdayOperatingTime weekdayOperatingTime,
                                     SaturdayOperatingTime saturdayOperatingTime,
                                     HolidayOperatingTime holidayOperatingTime, ReviewInfoResponse reviewInfo) {
        this.parkingName = parkingName;
        this.parkingType = parkingType;
        this.latitude = latitude;
        this.longitude = longitude;
        this.feeInfo = feeInfo;
        this.currentParkingCount = currentParkingCount;
        this.capacity = capacity;
        this.lastUpdated = lastUpdated;
        this.tel = tel;
        this.paymentType = paymentType;
        this.weekdayOperatingTime = weekdayOperatingTime;
        this.saturdayOperatingTime = saturdayOperatingTime;
        this.holidayOperatingTime = holidayOperatingTime;
        this.reviewInfo = reviewInfo;
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class FeeInfo {
        private Integer fee;
        private Integer time;

        public FeeInfo(Integer fee, Integer time) {
            this.fee = fee;
            this.time = time;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class WeekdayOperatingTime {
        private LocalTime beginTime;
        private LocalTime endTime;

        public WeekdayOperatingTime(LocalTime beginTime, LocalTime endTime) {
            this.beginTime = beginTime;
            this.endTime = endTime;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class SaturdayOperatingTime {
        private LocalTime beginTime;
        private LocalTime endTime;

        public SaturdayOperatingTime(LocalTime beginTime, LocalTime endTime) {
            this.beginTime = beginTime;
            this.endTime = endTime;
        }
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static class HolidayOperatingTime {
        private LocalTime beginTime;
        private LocalTime endTime;

        public HolidayOperatingTime(LocalTime beginTime, LocalTime endTime) {
            this.beginTime = beginTime;
            this.endTime = endTime;
        }
    }
}
