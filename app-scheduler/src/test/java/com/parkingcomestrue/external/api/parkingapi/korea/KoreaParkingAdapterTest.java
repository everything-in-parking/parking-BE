package com.parkingcomestrue.external.api.parkingapi.korea;

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
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;

class KoreaParkingAdapterTest {

    private static final int NO_PROVIDE = -1;

    private KoreaParkingAdapter adapter = new KoreaParkingAdapter();

    /**
     *  "주차장 이름": "횡계의원 뒤 1주차장",
     *  "주차장 운영 타입": "공영",
     *  "주차장 유형": "노외",
     *  "전화번호": "033-330-2285",
     *  "도로명 주소": "강원도 평창군 대관령면 축제길 40",
     *  "지번 주소": "강원도 평창군 대관령면 횡계리 321-10",
     *  "주차 구획수": "28",
     *  "운영 요일": "평일+토요일+공휴일",
     *  "평일 오픈 시간": "00:00",
     *  "평일 종료 시간": "23:59",
     *  "토요일 오픈 시간": "00:00",
     *  "토요일 종료 시간": "23:59",
     *  "휴일 오픈 시간": "00:00",
     *  "휴일 종료 시간": "23:59",
     *  "요금 정보": "무료",
     *  "기본 주차 시간": "0",
     *  "기본 주차 금액": "0",
     *  "추가 주차 시간": "",
     *  "추가 주차 금액": "",
     *  "하루 이용권 금액": "",
     *  "결제 방법": "카드전용",
     *  "longitude": "128.7079653",
     *  "latitude": "37.67170099",
     */
    @Test
    void 전국_주차장_표준_데이터를_변환한다() throws IOException {
        //given
        ObjectMapper objectMapper = new ObjectMapper();
        String path = "src/test/resources/korea-parking.json";
        KoreaParkingResponse response = objectMapper.readValue(new File(path), KoreaParkingResponse.class);

        //when
        Parking parking = adapter.convert(response).get(0);

        //then
        SoftAssertions.assertSoftly(
                soft -> {
                    soft.assertThat(parking.getBaseInformation().getName()).isEqualTo("횡계의원 뒤 1주차장");
                    soft.assertThat(parking.getBaseInformation().getParkingType()).isEqualTo(ParkingType.OFF_STREET);
                    soft.assertThat(parking.getBaseInformation().getOperationType()).isEqualTo(OperationType.PUBLIC);
                    soft.assertThat(parking.getBaseInformation().getAddress()).isEqualTo("강원도 평창군 대관령면 횡계리 321-10");
                    soft.assertThat(parking.getBaseInformation().getTel()).isEqualTo("033-330-2285");

                    soft.assertThat(parking.getSpace().getCapacity()).isEqualTo(28);
                    soft.assertThat(parking.getSpace().getCurrentParking()).isEqualTo(NO_PROVIDE);

                    soft.assertThat(parking.getLocation().getLongitude()).isEqualTo(128.7079653);
                    soft.assertThat(parking.getLocation().getLatitude()).isEqualTo(37.67170099);

                    soft.assertThat(parking.getFeePolicy().getBaseFee()).isEqualTo(Fee.ZERO);
                    soft.assertThat(parking.getFeePolicy().getBaseTimeUnit()).isEqualTo(TimeUnit.from(0));
                    soft.assertThat(parking.getFeePolicy().getExtraFee()).isEqualTo(Fee.NO_INFO);
                    soft.assertThat(parking.getFeePolicy().getExtraTimeUnit()).isEqualTo(TimeUnit.NO_INFO);
                    soft.assertThat(parking.getFeePolicy().getDayMaximumFee()).isEqualTo(Fee.NO_INFO);

                    soft.assertThat(parking.getOperatingTime().getWeekdayOperatingTime()).isEqualTo(TimeInfo.ALL_DAY);
                    soft.assertThat(parking.getOperatingTime().getSaturdayOperatingTime()).isEqualTo(TimeInfo.ALL_DAY);
                    soft.assertThat(parking.getOperatingTime().getHolidayOperatingTime()).isEqualTo(TimeInfo.ALL_DAY);

                    soft.assertThat(parking.getBaseInformation().getPayTypesDescription()).isEqualTo(PayType.CARD.getDescription());
                }
        );
    }
}
