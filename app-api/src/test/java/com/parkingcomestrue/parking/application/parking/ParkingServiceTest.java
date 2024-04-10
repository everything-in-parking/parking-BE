package com.parkingcomestrue.parking.application.parking;

import static org.junit.jupiter.api.Assertions.assertAll;

import com.parkingcomestrue.parking.application.container.ContainerTest;
import com.parkingcomestrue.parking.application.parking.dto.ParkingDetailInfoResponse;
import com.parkingcomestrue.parking.application.review.dto.ReviewCreateRequest;
import com.parkingcomestrue.parking.domain.member.Member;
import com.parkingcomestrue.parking.domain.member.Password;
import com.parkingcomestrue.parking.domain.parking.BaseInformation;
import com.parkingcomestrue.parking.domain.parking.Fee;
import com.parkingcomestrue.parking.domain.parking.FeePolicy;
import com.parkingcomestrue.parking.domain.parking.FreeOperatingTime;
import com.parkingcomestrue.parking.domain.parking.Location;
import com.parkingcomestrue.parking.domain.parking.OperatingTime;
import com.parkingcomestrue.parking.domain.parking.OperationType;
import com.parkingcomestrue.parking.domain.parking.Parking;
import com.parkingcomestrue.parking.domain.parking.ParkingType;
import com.parkingcomestrue.parking.domain.parking.PayTypes;
import com.parkingcomestrue.parking.domain.parking.Space;
import com.parkingcomestrue.parking.domain.parking.TimeInfo;
import com.parkingcomestrue.parking.domain.parking.TimeUnit;
import com.parkingcomestrue.parking.domain.review.Content;
import com.parkingcomestrue.parking.support.exception.DomainException;
import com.parkingcomestrue.parking.support.exception.ExceptionInformation;
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
                        Location.of(30d, 30d),
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
