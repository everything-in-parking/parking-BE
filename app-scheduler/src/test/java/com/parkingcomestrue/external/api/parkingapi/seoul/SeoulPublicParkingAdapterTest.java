package com.parkingcomestrue.external.api.parkingapi.seoul;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.parkingcomestrue.common.domain.parking.Fee;
import com.parkingcomestrue.common.domain.parking.OperationType;
import com.parkingcomestrue.common.domain.parking.Parking;
import com.parkingcomestrue.common.domain.parking.ParkingType;
import com.parkingcomestrue.common.domain.parking.PayType;
import com.parkingcomestrue.common.domain.parking.TimeInfo;
import com.parkingcomestrue.common.domain.parking.TimeUnit;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class SeoulPublicParkingAdapterTest {

    private SeoulPublicParkingAdapter adapter = new SeoulPublicParkingAdapter();

    /**
     * "주차장 코드": "1163833",
     * "주차장 이름": "봉천복개3 공영주차장(시)",
     * "주소": "관악구 봉천동 1467-3",
     * "주차장 유형": "노상 주차장",
     * "운영 구분": "1",
     * "전화번호": "",
     * "실시간 주차여부": "1",
     * "주차 구획수": 1.0, <- 62개
     * "실시간 주차수": 35.0,
     * "요금 정보": "유료",
     * "평일 오픈 시간": "0900",
     * "평일 종료 시간": "1900",
     * "토요일 오픈 시간": "0900",
     * "토요일 종료 시간": "1500",
     * "휴일 오픈 시간": "0000",
     * "휴일 종료 시간": "0000",
     * "토요일 요금 정보": "무료",
     * "주말 요금 정보": "유료",
     * "기본 주차 시간": 5.0,
     * "기본 주차 금액": 220.0,
     * "추가 주차 시간": 5.0,
     * "추가 주차 금액": 220.0,
     * "하루 최대 요금": 0.0,
     * "longitude": 126.9324736, (1번째 값)
     * "latitude": 37.48717617 (1번째 값)
     */
    @Test
    void 서울_주차장_데이터를_변환한다() throws IOException {
        //given
        ObjectMapper objectMapper = new ObjectMapper();
        String path = "src/test/resources/seoul-parking.json";
        SeoulPublicParkingResponse response = objectMapper.readValue(new File(path), SeoulPublicParkingResponse.class);

        //when
        Parking parking = adapter.convert(response).get(0);

        //then
        SoftAssertions.assertSoftly(
        soft -> {
            soft.assertThat(parking.getBaseInformation().getName()).isEqualTo("봉천복개3 공영주차장(시)");
            soft.assertThat(parking.getBaseInformation().getParkingType()).isEqualTo(ParkingType.ON_STREET);
            soft.assertThat(parking.getBaseInformation().getOperationType()).isEqualTo(OperationType.PUBLIC);
            soft.assertThat(parking.getBaseInformation().getAddress()).isEqualTo("관악구 봉천동 1467-3");
            soft.assertThat(parking.getBaseInformation().getTel()).isEqualTo("");

            soft.assertThat(parking.getSpace().getCapacity()).isEqualTo(62);
            soft.assertThat(parking.getSpace().getCurrentParking()).isEqualTo(35);

            soft.assertThat(parking.getLocation().getLongitude()).isEqualTo(126.9324736);
            soft.assertThat(parking.getLocation().getLatitude()).isEqualTo(37.48717617);

            soft.assertThat(parking.getFeePolicy().getBaseFee()).isEqualTo(Fee.from(220));
            soft.assertThat(parking.getFeePolicy().getBaseTimeUnit()).isEqualTo(TimeUnit.from(5));
            soft.assertThat(parking.getFeePolicy().getExtraFee()).isEqualTo(Fee.from(220));
            soft.assertThat(parking.getFeePolicy().getExtraTimeUnit()).isEqualTo(TimeUnit.from(5));
            soft.assertThat(parking.getFeePolicy().getDayMaximumFee()).isEqualTo(Fee.ZERO);

            soft.assertThat(parking.getOperatingTime().getWeekdayOperatingTime()).isEqualTo(new TimeInfo(LocalTime.of(9, 0), LocalTime.of(19, 0)));
            soft.assertThat(parking.getOperatingTime().getSaturdayOperatingTime()).isEqualTo(new TimeInfo(LocalTime.of(9, 0), LocalTime.of(15, 0)));
            soft.assertThat(parking.getOperatingTime().getHolidayOperatingTime()).isEqualTo(new TimeInfo(LocalTime.of(0, 0), LocalTime.of(0, 0)));

            soft.assertThat(parking.getBaseInformation().getPayTypesDescription()).isEqualTo(PayType.NO_INFO.getDescription());
        }
        );
    }
}
