package com.example.parking.application.review;

import com.example.parking.application.review.dto.ReviewCreateRequest;
import com.example.parking.domain.common.Association;
import com.example.parking.domain.review.Review;
import com.example.parking.domain.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    @Transactional
    public Long createReview(Long parkingId, Long reviewerId, ReviewCreateRequest request) {
        if (reviewRepository.existsByParkingIdAndReviewerId(Association.from(parkingId),
                Association.from(reviewerId))) {
            throw new IllegalStateException("유저가 해당 주차장에 대해 이미 리뷰를 작성하였습니다.");
        }

        Review review = new Review(Association.from(parkingId), Association.from(reviewerId), request.toContents());
        reviewRepository.save(review);
        return review.getId();
    }
}
