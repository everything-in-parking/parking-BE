package com.parkingcomestrue.parking.application;

import static org.assertj.core.api.Assertions.assertThat;

import com.parkingcomestrue.parking.domain.BaseInformation;
import com.parkingcomestrue.parking.domain.Fee;
import com.parkingcomestrue.parking.domain.FeePolicy;
import com.parkingcomestrue.parking.domain.FreeOperatingTime;
import com.parkingcomestrue.parking.domain.OperationType;
import com.parkingcomestrue.parking.domain.Parking;
import com.parkingcomestrue.parking.domain.ParkingFeeCalculator;
import com.parkingcomestrue.parking.domain.ParkingType;
import com.parkingcomestrue.parking.domain.PayType;
import com.parkingcomestrue.parking.domain.PayTypes;
import com.parkingcomestrue.parking.application.dto.SearchingCondition;
import com.parkingcomestrue.parking.domain.TimeUnit;
import com.parkingcomestrue.parking.application.ParkingFilteringService;
import com.parkingcomestrue.searchcondition.domain.FeeType;
import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ParkingFilteringServiceTest {

    private final ParkingFilteringService parkingFilteringService = new ParkingFilteringService(
            new ParkingFeeCalculator());

    @Test
    void 조회조건에_따라_주차장을_필터링한다1() {
        // given
        ParkingType parkingTypeCondition = ParkingType.MECHANICAL;
        OperationType operationTypeCondition = OperationType.PUBLIC;
        PayType wantPayType = PayType.CASH;

        Parking wantParking = Parking.builder()
                .baseInformation(new BaseInformation("name", "tell", "address",
                        PayTypes.from(List.of(wantPayType)),
                        parkingTypeCondition,
                        operationTypeCondition))
                .build();

        Parking notWantParking1 = Parking.builder()
                .baseInformation(new BaseInformation("name", "tell", "address",
                        PayTypes.DEFAULT,
                        parkingTypeCondition,
                        OperationType.NO_INFO))
                .build();

        Parking notWantParking2 = Parking.builder()
                .baseInformation(new BaseInformation("name", "tell", "address",
                        PayTypes.DEFAULT,
                        ParkingType.OFF_STREET,
                        operationTypeCondition))
                .build();

        // when
        SearchingCondition searchingCondition = new SearchingCondition(
                List.of(operationTypeCondition),
                List.of(parkingTypeCondition),
                List.of(wantPayType),
                FeeType.PAID, 3);

        List<Parking> filterList = parkingFilteringService.filterByCondition(
                List.of(wantParking, notWantParking1, notWantParking2),
                searchingCondition,
                LocalDateTime.now()
        );

        // then
        assertThat(filterList).hasSize(1);
    }

    @Test
    void 조회조건에_따라_주차장을_필터링한다2() {
        // given
        ParkingType wantParkingTypeCondition = ParkingType.ON_STREET;
        OperationType wantOperationTypeCondition = OperationType.PUBLIC;
        PayType wantPayType = PayType.CARD;

        Parking wantParking = Parking.builder()
                .baseInformation(new BaseInformation("name", "tel", "address",
                        PayTypes.from(List.of(wantPayType)),
                        wantParkingTypeCondition,
                        wantOperationTypeCondition))
                .build();

        Parking notWantParking1 = Parking.builder()
                .baseInformation(new BaseInformation("name", "tel", "address",
                        PayTypes.DEFAULT,
                        ParkingType.MECHANICAL,
                        wantOperationTypeCondition))
                .build();

        Parking notWantParking2 = Parking.builder()
                .baseInformation(new BaseInformation("name", "tel", "address",
                        PayTypes.DEFAULT,
                        ParkingType.NO_INFO,
                        wantOperationTypeCondition))
                .build();

        // when
        SearchingCondition searchingCondition = new SearchingCondition(
                List.of(wantOperationTypeCondition),
                List.of(wantParkingTypeCondition),
                List.of(wantPayType),
                FeeType.PAID, 3);

        List<Parking> result = parkingFilteringService.filterByCondition(
                List.of(wantParking, notWantParking1, notWantParking2),
                searchingCondition,
                LocalDateTime.now()
        );

        // then
        assertThat(result).hasSize(1);
    }

    @Test
    void 조회조건이_무료일_때_예상요금이_0인_주차장만_조회된다() {
        // given - 하루종일 무료 주차장 2개, 유료 주차장 1개
        FeePolicy freeFeePolicy = new FeePolicy(Fee.ZERO, Fee.ZERO, TimeUnit.from(10), TimeUnit.from(10), Fee.ZERO);

        OperationType operationType = OperationType.PUBLIC;
        ParkingType parkingType = ParkingType.MECHANICAL;
        BaseInformation baseInformation = new BaseInformation("name", "tel", "address",
                PayTypes.from(List.of(PayType.CARD)),
                parkingType,
                operationType
        );
        Parking freeParking1 = Parking.builder()
                .baseInformation(baseInformation)
                .freeOperatingTime(FreeOperatingTime.ALWAYS_FREE)
                .feePolicy(freeFeePolicy)
                .build();

        Parking freeParking2 = Parking.builder()
                .baseInformation(baseInformation)
                .freeOperatingTime(FreeOperatingTime.ALWAYS_FREE)
                .feePolicy(freeFeePolicy)
                .build();

        FeePolicy paidFeePolicy = new FeePolicy(Fee.from(100), Fee.from(200), TimeUnit.from(1), TimeUnit.from(12),
                Fee.from(1000));
        Parking paidParking = Parking.builder()
                .baseInformation(baseInformation)
                .freeOperatingTime(FreeOperatingTime.ALWAYS_PAY)
                .feePolicy(paidFeePolicy)
                .build();

        // when - 검색조건이 Free 인 filterCondition 으로 주차장 필터링
        SearchingCondition searchingCondition = new SearchingCondition(List.of(operationType), List.of(parkingType),
                List.of(PayType.CARD), FeeType.FREE, 3);
        List<Parking> filteredParkings = parkingFilteringService.filterByCondition(
                List.of(freeParking1, freeParking2, paidParking),
                searchingCondition,
                LocalDateTime.now()
        );

        // then
        Assertions.assertThat(filteredParkings).hasSize(2);

    }
}
