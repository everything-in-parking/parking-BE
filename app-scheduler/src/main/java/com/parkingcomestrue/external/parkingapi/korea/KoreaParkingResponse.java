package com.parkingcomestrue.external.parkingapi.korea;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

@Getter
public class KoreaParkingResponse {

    private Response response;

    @Getter
    public static class Response {

        private Header header;
        private Body body;

        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Header {

            private String resultCode;
            private String resultMsg;
        }

        @Getter
        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Body {

            private List<Item> items;

            @Getter
            @JsonIgnoreProperties(ignoreUnknown = true)
            public static class Item {

                @JsonProperty("prkplceNm")
                private String parkingName;

                @JsonProperty("prkplceSe")
                private String operationType;

                @JsonProperty("prkplceType")
                private String parkingType;

                @JsonProperty("rdnmadr")
                private String newAddress;

                @JsonProperty("lnmadr")
                private String oldAddress;

                @JsonProperty("prkcmprt")
                private String capacity;

                @JsonProperty("weekdayOperOpenHhmm")
                private String weekDayBeginTime;

                @JsonProperty("weekdayOperColseHhmm")
                private String weekdayEndTime;

                @JsonProperty("satOperOperOpenHhmm")
                private String saturdayBeginTime;

                @JsonProperty("satOperCloseHhmm")
                private String saturdayEndTime;

                @JsonProperty("holidayOperOpenHhmm")
                private String holidayBeginTime;

                @JsonProperty("holidayCloseOpenHhmm")
                private String holidayEndTime;

                @JsonProperty("metpay")
                private String payType;

                @JsonProperty("parkingchrgeInfo")
                private String feeInfo;

                @JsonProperty("basicTime")
                private String baseTimeUnit;

                @JsonProperty("basicCharge")
                private String baseFee;

                @JsonProperty("addUnitTime")
                private String extraTimeUnit;

                @JsonProperty("addUnitCharge")
                private String extraFee;

                @JsonProperty("dayCmmtktAdjTime")
                private String dayMaximumTime;

                @JsonProperty("dayCmmtkt")
                private String dayMaximumFee;

                @JsonProperty("phoneNumber")
                private String tel;

                @JsonProperty("latitude")
                private String latitude;

                @JsonProperty("longitude")
                private String longitude;
            }
        }
    }
}
