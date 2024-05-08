package com.parkingcomestrue.external.parkingapi.pusan;

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

class PusanPublicParkingAdapterTest {

    private static final int NO_PROVIDE = -1;

    private PusanPublicParkingAdapter adapter = new PusanPublicParkingAdapter();

    /**
     *  "주차장 이름": "백양고가도로주변",
     *  "주차장 운영 타입": "공영",
     *  "주차장 유형": "노외",
     *  "전화번호": "051-310-4501",
     *  "도로명 주소": "-",
     *  "지번 주소": "사상구 주례1동 340",
     *  "주차 구획수": "23",
     *  "실시간주차수": "-",
     *  "운영 요일": "-",
     *  "평일 오픈 시간": "00:00",
     *  "평일 종료 시간": "24:00",
     *  "토요일 오픈 시간": "00:00",
     *  "토요일 종료 시간": "24:00",
     *  "휴일 오픈 시간": "00:00",
     *  "휴일 종료 시간": "24:00",
     *  "요금정보": "-",
     *  "기본 주차 시간": "10",
     *  "기본 주차 금액": "0",
     *  "추가 주차 시간": "10",
     *  "추가 주차 금액": "0",
     *  "하루 이용권 금액": "0",
     *  "결제 방법": "-",
     *  "longitude": "-",
     *  "latitude": "-",
     */
    @Test
    void 부산_주차장_데이터를_변환한다() throws IOException {
        //given
        ObjectMapper objectMapper = new ObjectMapper();
        String path = "src/test/resources/pusan-parking.json";
        PusanPublicParkingResponse response = objectMapper.readValue(new File(path), PusanPublicParkingResponse.class);

        //when
        Parking parking = adapter.convert(response).get(0);

        //then
        SoftAssertions.assertSoftly(
                soft -> {
                    soft.assertThat(parking.getBaseInformation().getName()).isEqualTo("백양고가도로주변");
                    soft.assertThat(parking.getBaseInformation().getParkingType()).isEqualTo(ParkingType.OFF_STREET);
                    soft.assertThat(parking.getBaseInformation().getOperationType()).isEqualTo(OperationType.PUBLIC);
                    soft.assertThat(parking.getBaseInformation().getAddress()).isEqualTo("사상구 주례1동 340");
                    soft.assertThat(parking.getBaseInformation().getTel()).isEqualTo("051-310-4501");

                    soft.assertThat(parking.getSpace().getCapacity()).isEqualTo(23);
                    soft.assertThat(parking.getSpace().getCurrentParking()).isEqualTo(NO_PROVIDE);

                    soft.assertThat(parking.getLocation().getLongitude()).isEqualTo(NO_PROVIDE);
                    soft.assertThat(parking.getLocation().getLatitude()).isEqualTo(NO_PROVIDE);

                    soft.assertThat(parking.getFeePolicy().getBaseFee()).isEqualTo(Fee.ZERO);
                    soft.assertThat(parking.getFeePolicy().getBaseTimeUnit()).isEqualTo(TimeUnit.from(10));
                    soft.assertThat(parking.getFeePolicy().getExtraFee()).isEqualTo(Fee.ZERO);
                    soft.assertThat(parking.getFeePolicy().getExtraTimeUnit()).isEqualTo(TimeUnit.from(10));
                    soft.assertThat(parking.getFeePolicy().getDayMaximumFee()).isEqualTo(Fee.ZERO);

                    soft.assertThat(parking.getOperatingTime().getWeekday()).isEqualTo(TimeInfo.ALL_DAY);
                    soft.assertThat(parking.getOperatingTime().getSaturday()).isEqualTo(TimeInfo.ALL_DAY);
                    soft.assertThat(parking.getOperatingTime().getHoliday()).isEqualTo(TimeInfo.ALL_DAY);

                    soft.assertThat(parking.getBaseInformation().getPayTypesDescription()).isEqualTo(PayType.NO_INFO.getDescription());
                }
        );
    }
}
