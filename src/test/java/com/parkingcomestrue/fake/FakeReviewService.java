package com.parkingcomestrue.fake;

import com.parkingcomestrue.review.application.ReviewService;
import com.parkingcomestrue.review.domain.ReviewRepository;
import com.parkingcomestrue.review.service.ReviewDomainService;

public class FakeReviewService extends ReviewService {
    public FakeReviewService(ReviewRepository reviewRepository,
                             ReviewDomainService reviewDomainService) {
        super(reviewRepository, reviewDomainService);
    }
}
