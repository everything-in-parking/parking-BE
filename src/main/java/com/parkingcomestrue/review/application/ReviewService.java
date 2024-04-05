package com.parkingcomestrue.review.application;

import com.parkingcomestrue.review.application.dto.ReviewCountResponse;
import com.parkingcomestrue.review.application.dto.ReviewCreateRequest;
import com.parkingcomestrue.review.application.dto.ReviewInfoResponse;
import com.parkingcomestrue.support.Association;
import com.parkingcomestrue.review.domain.Content;
import com.parkingcomestrue.review.domain.Review;
import com.parkingcomestrue.review.domain.ReviewRepository;
import com.parkingcomestrue.review.service.ReviewDomainService;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewDomainService reviewDomainService;

    @Transactional
    public Long createReview(Long parkingId, Long reviewerId, ReviewCreateRequest request) {
        reviewDomainService.validateDuplicateReview(Association.from(parkingId), Association.from(reviewerId));

        Review review = new Review(Association.from(parkingId), Association.from(reviewerId), request.toContents());
        reviewRepository.save(review);
        return review.getId();
    }

    public ReviewInfoResponse readReviews(Long parkingId) {
        List<Review> reviews = reviewRepository.findAllByParkingId(Association.from(parkingId));
        Map<Content, Long> counts = reviewDomainService.collectByContent(reviews);
        return new ReviewInfoResponse(reviewDomainService.calculateTotalReviews(counts), orderByCounts(counts));
    }

    private List<ReviewCountResponse> orderByCounts(Map<Content, Long> counts) {
        return counts.keySet().stream()
                .map(content -> new ReviewCountResponse(content.getDescription(), counts.get(content).intValue()))
                .sorted(Comparator.comparing(ReviewCountResponse::count).reversed())
                .toList();
    }
}
