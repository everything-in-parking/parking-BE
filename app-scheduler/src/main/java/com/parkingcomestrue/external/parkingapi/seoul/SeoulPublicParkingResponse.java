package com.parkingcomestrue.external.parkingapi.seoul;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import java.util.List;
import lombok.Getter;

@Getter
public class SeoulPublicParkingResponse {

    @JsonProperty("GetParkingInfo")
    private ParkingInfo parkingInfo;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ParkingInfo {

        @JsonProperty("row")
        private List<SeoulCityParking> rows;

        @Getter
        @JsonNaming(value = PropertyNamingStrategies.UpperSnakeCaseStrategy.class)
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class SeoulCityParking {

            private String parkingCode;
            private String parkingName;
            private String parkingTypeNM;
            private String operationRule;
            private String tel;

            private String addr;
            private String lat;
            private String lng;

            private Integer queStatus;
            private Integer capacity;
            private Integer curParking;

            private String payNM;
            private String saturdayPayNM;
            private String holidayPayNM;
            private Integer rates;
            private Integer timeRate;
            private Integer addRates;
            private Integer addTimeRate;
            private Integer dayMaximum;

            private String weekdayBeginTime;
            private String weekdayEndTime;
            private String weekendBeginTime;
            private String weekendEndTime;
            private String holidayBeginTime;
            private String holidayEndTime;

            public void setCapacity(int capacity) {
                this.capacity = capacity;
            }
        }
    }
}
