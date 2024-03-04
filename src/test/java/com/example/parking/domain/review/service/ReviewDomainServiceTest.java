package com.example.parking.domain.review.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.example.parking.support.Association;
import com.example.parking.domain.member.Member;
import com.example.parking.domain.parking.Parking;
import com.example.parking.domain.review.Content;
import com.example.parking.domain.review.Review;
import com.example.parking.fake.BasicReviewRepository;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ReviewDomainServiceTest {

    private final BasicReviewRepository reviewRepository = new BasicReviewRepository();
    private final ReviewDomainService reviewDomainService = new ReviewDomainService(reviewRepository);

    @Test
    void 같은_주차장에_리뷰를_작성하면_예외가_발생한다() {
        //given
        Association<Parking> parkingId = Association.from(1L);
        Association<Member> reviewerId = Association.from(1L);
        reviewRepository.save(new Review(parkingId, reviewerId, List.of(Content.LOW_PRICE)));

        //when, then
        assertThatThrownBy(() -> reviewDomainService.validateDuplicateReview(parkingId, reviewerId))
                .isInstanceOf(IllegalStateException.class);
    }

    @Test
    void 리뷰를_내용별로_집계한다() {
        //given
        List<Review> reviews = List.of(
                new Review(Association.from(1L), Association.from(1L),
                        List.of(Content.LOW_PRICE)
                ),
                new Review(Association.from(1L), Association.from(2L),
                        List.of(Content.LOW_PRICE, Content.EASY_TO_PAY)
                ),
                new Review(Association.from(1L), Association.from(3L),
                        List.of(Content.LOW_PRICE, Content.EASY_TO_PAY, Content.GOOD_ACCESSIBILITY)
                )
        );
        //when
        Map<Content, Long> actual = reviewDomainService.collectByContent(reviews);

        //then
        assertSoftly(soft -> {
            soft.assertThat(actual.get(Content.LOW_PRICE)).isEqualTo(3L);
            soft.assertThat(actual.get(Content.EASY_TO_PAY)).isEqualTo(2L);
            soft.assertThat(actual.get(Content.GOOD_ACCESSIBILITY)).isEqualTo(1L);
        });
    }

    @Test
    void 전체_리뷰수를_구한다() {
        //given
        List<Content> contents1 = List.of(Content.LOW_PRICE);
        List<Content> contents2 = List.of(Content.LOW_PRICE, Content.EASY_TO_PAY);
        List<Content> contents3 = List.of(Content.LOW_PRICE, Content.EASY_TO_PAY, Content.GOOD_ACCESSIBILITY);
        List<Review> reviews = List.of(
                new Review(Association.from(1L), Association.from(1L),
                        contents1
                ),
                new Review(Association.from(1L), Association.from(2L),
                        contents2
                ),
                new Review(Association.from(1L), Association.from(3L),
                        contents3
                )
        );
        Map<Content, Long> counts = reviewDomainService.collectByContent(reviews);

        //when
        int actual = reviewDomainService.calculateTotalReviews(counts);

        //then
        assertThat(actual).isEqualTo(contents1.size() + contents2.size() + contents3.size());
    }
}
