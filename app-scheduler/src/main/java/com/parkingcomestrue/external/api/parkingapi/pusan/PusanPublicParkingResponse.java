package com.parkingcomestrue.external.api.parkingapi.pusan;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

@Getter
public class PusanPublicParkingResponse {

    @JsonProperty("getPblcPrkngInfo")
    private ParkingInfo getParkingInfoDetails;

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ParkingInfo {

        private Header header;
        private Body body;

        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Header {
            private String resultCode;
        }

        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Body {
            private Items items;
            private int totalCount;
        }

        @Getter
        public static class Items {
            private List<Item> item;
        }

        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Item {

            @JsonProperty("pkNam")
            private String parkingName;

            @JsonProperty("pkFm")
            private String parkingTypeNM;

            @JsonProperty("tponNum")
            private String telephoneNumber;

            @JsonProperty("payMtd")
            private String payType;

            @JsonProperty("doroAddr")
            private String newAddress;

            @JsonProperty("jibunAddr")
            private String oldAddress;

            @JsonProperty("yCdnt")
            private String latitude;

            @JsonProperty("xCdnt")
            private String longitude;

            @JsonProperty("pkCnt")
            private String capacity;

            @JsonProperty("currava")
            private String curParking;


            @JsonProperty("svcSrtTe")
            private String weekdayBeginTime;

            @JsonProperty("svcEndTe")
            private String weekdayEndTime;

            @JsonProperty("satSrtTe")
            private String weekendBeginTime;

            @JsonProperty("satEndTe")
            private String weekendEndTime;

            @JsonProperty("hldSrtTe")
            private String holidayBeginTime;

            @JsonProperty("hldEndTe")
            private String holidayEndTime;


            @JsonProperty("feeInfo")
            private String payNM;

            @JsonProperty("tenMin")
            private String rates;

            @JsonProperty("pkBascTime")
            private String timeRate;

            @JsonProperty("feeAdd")
            private String addRates;

            @JsonProperty("pkAddTime")
            private String addTimeRate;

            @JsonProperty("ftDay")
            private String dayMaximum;
        }
    }
}
