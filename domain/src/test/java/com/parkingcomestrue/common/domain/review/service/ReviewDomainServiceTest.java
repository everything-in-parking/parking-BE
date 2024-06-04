package com.parkingcomestrue.common.domain.review.service;

import static com.parkingcomestrue.common.support.exception.DomainExceptionInformation.DUPLICATE_REVIEW;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import com.parkingcomestrue.common.domain.member.Member;
import com.parkingcomestrue.common.domain.parking.Parking;
import com.parkingcomestrue.common.domain.review.Content;
import com.parkingcomestrue.common.domain.review.Review;
import com.parkingcomestrue.common.domain.review.service.ReviewDomainService;
import com.parkingcomestrue.common.support.Association;
import com.parkingcomestrue.common.support.exception.DomainException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;
import repository.BasicReviewRepository;

class ReviewDomainServiceTest {

    private final BasicReviewRepository reviewRepository = new BasicReviewRepository();
    private final ReviewDomainService reviewDomainService = new ReviewDomainService(reviewRepository);

    @Test
    void 같은_주차장에_리뷰를_작성하면_예외가_발생한다() {
        //given
        Association<Parking> parkingId = Association.from(1L);
        Association<Member> reviewerId = Association.from(1L);
        reviewRepository.save(new Review(parkingId, reviewerId, Set.of(Content.LOW_PRICE)));

        //when, then
        assertThatThrownBy(() -> reviewDomainService.validateDuplicateReview(parkingId, reviewerId))
                .isInstanceOf(DomainException.class)
                .hasMessage(DUPLICATE_REVIEW.getMessage());
    }

    @Test
    void 리뷰를_내용별로_집계한다() {
        //given
        List<Review> reviews = List.of(
                new Review(Association.from(1L), Association.from(1L),
                        Set.of(Content.LOW_PRICE)
                ),
                new Review(Association.from(1L), Association.from(2L),
                        Set.of(Content.LOW_PRICE, Content.EASY_TO_PAY)
                ),
                new Review(Association.from(1L), Association.from(3L),
                        Set.of(Content.LOW_PRICE, Content.EASY_TO_PAY, Content.GOOD_ACCESSIBILITY)
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
        Set<Content> contents1 = Set.of(Content.LOW_PRICE);
        Set<Content> contents2 = Set.of(Content.LOW_PRICE, Content.EASY_TO_PAY);
        Set<Content> contents3 = Set.of(Content.LOW_PRICE, Content.EASY_TO_PAY, Content.GOOD_ACCESSIBILITY);
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
