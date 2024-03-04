package com.example.parking.application.review;

import com.example.parking.application.review.dto.ReviewCountResponse;
import com.example.parking.application.review.dto.ReviewCreateRequest;
import com.example.parking.application.review.dto.ReviewInfoResponse;
import com.example.parking.domain.common.Association;
import com.example.parking.domain.review.Content;
import com.example.parking.domain.review.Review;
import com.example.parking.domain.review.ReviewRepository;
import com.example.parking.domain.review.service.ReviewDomainService;
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
