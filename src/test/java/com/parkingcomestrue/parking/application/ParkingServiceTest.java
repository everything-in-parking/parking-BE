package com.parkingcomestrue.parking.application;

import static org.junit.jupiter.api.Assertions.assertAll;

import com.parkingcomestrue.parking.application.dto.ParkingDetailInfoResponse;
import com.parkingcomestrue.review.application.dto.ReviewCreateRequest;
import com.parkingcomestrue.container.ContainerTest;
import com.parkingcomestrue.member.domain.Member;
import com.parkingcomestrue.member.domain.Password;
import com.parkingcomestrue.parking.domain.BaseInformation;
import com.parkingcomestrue.parking.domain.Fee;
import com.parkingcomestrue.parking.domain.FeePolicy;
import com.parkingcomestrue.parking.domain.FreeOperatingTime;
import com.parkingcomestrue.parking.domain.Location;
import com.parkingcomestrue.parking.domain.OperatingTime;
import com.parkingcomestrue.parking.domain.OperationType;
import com.parkingcomestrue.parking.domain.Parking;
import com.parkingcomestrue.parking.domain.ParkingType;
import com.parkingcomestrue.parking.domain.PayTypes;
import com.parkingcomestrue.parking.domain.Space;
import com.parkingcomestrue.parking.domain.TimeInfo;
import com.parkingcomestrue.parking.domain.TimeUnit;
import com.parkingcomestrue.review.domain.Content;
import com.parkingcomestrue.support.exception.DomainException;
import com.parkingcomestrue.support.exception.ExceptionInformation;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class ParkingServiceTest extends ContainerTest {

    @Test
    void 조회하려는_주차장이_없으면_예외() {
        //given
        Assertions.assertThatThrownBy(() -> parkingService.findParking(1L))
                .isInstanceOf(DomainException.class)
                .hasMessage(ExceptionInformation.INVALID_PARKING.getMessage());
    }

    @Test
    void 주차장_상세조회() {
        //given
        String parkingName = "호이주차장";
        Parking parking = makeParking(parkingName);
        List<Parking> parkings = List.of(parking);
        parkingService.saveAll(parkings);

        Member member = new Member( "email", "하디", new Password("qwer1234"));
        memberRepository.save(member);

        ReviewCreateRequest reviewCreateRequest = new ReviewCreateRequest(
                List.of(Content.LARGE_PARKING_SPACE.getDescription(), Content.EASY_TO_PAY.getDescription()));
        reviewService.createReview(parking.getId(), member.getId(), reviewCreateRequest);

        // when, then
        ParkingDetailInfoResponse parkingDetailInfoResponse = parkingService.findParking(parking.getId());
        assertAll(
                () -> Assertions.assertThat(parkingDetailInfoResponse.getReviewInfo().reviews()).hasSize(2),
                () -> Assertions.assertThat(parkingDetailInfoResponse.getParkingName()).isEqualTo(parkingName)
        );
    }

    private Parking makeParking(String parkingName) {
        return new Parking
                (
                        new BaseInformation(parkingName, "010", "부산", PayTypes.DEFAULT, ParkingType.MECHANICAL,
                                OperationType.PRIVATE),
                        Location.of(140d, 37d),
                        Space.of(100, 30),
                        new FreeOperatingTime(
                                new TimeInfo(LocalTime.of(10, 30), LocalTime.of(20, 30)),
                                new TimeInfo(LocalTime.of(10, 30), LocalTime.of(20, 30)),
                                new TimeInfo(LocalTime.of(10, 30), LocalTime.of(20, 30))
                        ),
                        new OperatingTime(
                                new TimeInfo(LocalTime.of(10, 30), LocalTime.of(20, 30)),
                                new TimeInfo(LocalTime.of(10, 30), LocalTime.of(20, 30)),
                                new TimeInfo(LocalTime.of(10, 30), LocalTime.of(20, 30))
                        ),
                        new FeePolicy(
                                Fee.from(2000),
                                Fee.from(2000),
                                TimeUnit.from(30),
                                TimeUnit.from(15),
                                Fee.from(50000))
                );
    }

}
