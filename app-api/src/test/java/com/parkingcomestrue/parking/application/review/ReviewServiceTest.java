package com.parkingcomestrue.parking.application.review;

import static com.parkingcomestrue.common.support.exception.DomainExceptionInformation.DUPLICATE_REVIEW;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.parkingcomestrue.parking.application.member.dto.MemberId;
import com.parkingcomestrue.parking.application.review.dto.ReviewCreateRequest;
import com.parkingcomestrue.parking.application.review.dto.ReviewInfoResponse;
import com.parkingcomestrue.common.domain.member.Member;
import com.parkingcomestrue.common.domain.parking.Parking;
import com.parkingcomestrue.common.domain.review.Content;
import com.parkingcomestrue.common.domain.review.Review;
import com.parkingcomestrue.common.domain.review.service.ReviewDomainService;
import com.parkingcomestrue.common.support.Association;
import com.parkingcomestrue.common.support.exception.DomainException;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import repository.BasicMemberRepository;
import repository.BasicParkingRepository;
import repository.BasicReviewRepository;

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
        Long reviewId = reviewService.createReview(parking.getId(), MemberId.from(reviewer.getId()), request);

        //then
        assertThat(reviewId).isNotNull();
    }

    @Test
    void 리뷰를_이미_작성했으면_예외가_발생한다() {
        //given
        Parking parking = parkingRepository.saveAndGet(1).get(0);
        Member reviewer = memberRepository.saveAndGet(1).get(0);
        ReviewCreateRequest request = new ReviewCreateRequest(List.of("주차 자리가 많아요", "결제가 편리해요"));
        reviewService.createReview(parking.getId(), MemberId.from(reviewer.getId()), request);

        //when, then
        assertThatThrownBy(() -> reviewService.createReview(parking.getId(), MemberId.from(reviewer.getId()), request))
                .isInstanceOf(DomainException.class)
                .hasMessage(DUPLICATE_REVIEW.getMessage());
    }

    @Test
    void 주차장에_대한_리뷰를_내용이_많은순으로_가져온다() {
        //given
        Parking parking = parkingRepository.saveAndGet(1).get(0);
        Association<Parking> parkingId = Association.from(parking.getId());
        List<Member> reviewers = memberRepository.saveAndGet(3);

        Set<Content> contents1 = Set.of(Content.LOW_PRICE);
        Set<Content> contents2 = Set.of(Content.LOW_PRICE, Content.EASY_TO_PAY);
        Set<Content> contents3 = Set.of(Content.LOW_PRICE, Content.EASY_TO_PAY, Content.GOOD_ACCESSIBILITY);
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
