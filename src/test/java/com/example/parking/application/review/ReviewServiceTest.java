package com.example.parking.application.review;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.parking.application.review.dto.ReviewCreateRequest;
import com.example.parking.domain.member.Member;
import com.example.parking.domain.parking.Parking;
import com.example.parking.domain.review.PrivateImageStorage;
import com.example.parking.fake.BasicMemberRepository;
import com.example.parking.fake.BasicParkingRepository;
import com.example.parking.fake.BasicReviewRepository;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReviewServiceTest {

    private final BasicParkingRepository parkingRepository = new BasicParkingRepository();
    private final BasicMemberRepository memberRepository = new BasicMemberRepository();
    private final ReviewService reviewService = new ReviewService(
            new BasicReviewRepository(),
            parkingRepository,
            memberRepository,
            new PrivateImageStorage(null)
    );

    @Test
    void 리뷰를_작성한다() {
        //given
        Parking parking = parkingRepository.saveAndGet(1).get(0);
        Member reviewer = memberRepository.saveAndGet(1).get(0);
        ReviewCreateRequest request = new ReviewCreateRequest(List.of("주차 자리가 많아요", "결제가 편리해요"), null);

        //when
        Long reviewId = reviewService.createReview(parking.getId(), reviewer.getId(), request);

        //then
        assertThat(reviewId).isNotNull();
    }

    @Test
    void 리뷰를_이미_작성했으면_예외가_발생한다() {
        //given
        Parking parking = parkingRepository.saveAndGet(1).get(0);
        Member reviewer = memberRepository.saveAndGet(1).get(0);
        ReviewCreateRequest request = new ReviewCreateRequest(List.of("주차 자리가 많아요", "결제가 편리해요"), null);
        reviewService.createReview(parking.getId(), reviewer.getId(), request);

        //when, then
        assertThatThrownBy(() -> reviewService.createReview(parking.getId(), reviewer.getId(), request))
                .isInstanceOf(IllegalStateException.class);
    }
}
