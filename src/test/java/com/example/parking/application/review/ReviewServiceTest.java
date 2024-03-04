package com.example.parking.application.review;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.example.parking.application.review.dto.ReviewCreateRequest;
import com.example.parking.application.review.dto.ReviewInfoResponse;
import com.example.parking.support.Association;
import com.example.parking.domain.member.Member;
import com.example.parking.domain.parking.Parking;
import com.example.parking.domain.review.Content;
import com.example.parking.domain.review.Review;
import com.example.parking.domain.review.service.ReviewDomainService;
import com.example.parking.fake.BasicMemberRepository;
import com.example.parking.fake.BasicParkingRepository;
import com.example.parking.fake.BasicReviewRepository;
import java.util.List;
import org.junit.jupiter.api.Test;

class ReviewServiceTest {

    private final BasicParkingRepository parkingRepository = new BasicParkingRepository();
    private final BasicMemberRepository memberRepository = new BasicMemberRepository();
    private final BasicReviewRepository reviewRepository = new BasicReviewRepository();
    private final ReviewService reviewService = new ReviewService(
            reviewRepository,
            new ReviewDomainService(reviewRepository)
    );

    @Test
    void 리뷰를_작성한다() {
        //given
        Parking parking = parkingRepository.saveAndGet(1).get(0);
        Member reviewer = memberRepository.saveAndGet(1).get(0);
        ReviewCreateRequest request = new ReviewCreateRequest(List.of("주차 자리가 많아요", "결제가 편리해요"));

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
        ReviewCreateRequest request = new ReviewCreateRequest(List.of("주차 자리가 많아요", "결제가 편리해요"));
        reviewService.createReview(parking.getId(), reviewer.getId(), request);

        //when, then
        assertThatThrownBy(() -> reviewService.createReview(parking.getId(), reviewer.getId(), request))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 주차장에_대한_리뷰를_내용이_많은순으로_가져온다() {
        //given
        Parking parking = parkingRepository.saveAndGet(1).get(0);
        Association<Parking> parkingId = Association.from(parking.getId());
        List<Member> reviewers = memberRepository.saveAndGet(3);

        List<Content> contents1 = List.of(Content.LOW_PRICE);
        List<Content> contents2 = List.of(Content.LOW_PRICE, Content.EASY_TO_PAY);
        List<Content> contents3 = List.of(Content.LOW_PRICE, Content.EASY_TO_PAY, Content.GOOD_ACCESSIBILITY);
        reviewRepository.save(
                new Review(parkingId, Association.from(reviewers.get(0).getId()), contents1)
        );
        reviewRepository.save(
                new Review(parkingId, Association.from(reviewers.get(1).getId()), contents2)
        );
        reviewRepository.save(
                new Review(parkingId, Association.from(reviewers.get(2).getId()), contents3)
        );

        //when
        ReviewInfoResponse reviewInfoResponse = reviewService.readReviews(parkingId.getId());

        //then
        assertSoftly(
                soft -> {
                    soft.assertThat(reviewInfoResponse.totalReviewCount())
                            .isEqualTo(contents1.size() + contents2.size() + contents3.size());
                    soft.assertThat(reviewInfoResponse.reviews().get(0).content())
                            .isEqualTo(Content.LOW_PRICE.getDescription());
                    soft.assertThat(reviewInfoResponse.reviews().get(1).content())
                            .isEqualTo(Content.EASY_TO_PAY.getDescription());
                    soft.assertThat(reviewInfoResponse.reviews().get(2).content())
                            .isEqualTo(Content.GOOD_ACCESSIBILITY.getDescription());
                }
        );
    }
}
