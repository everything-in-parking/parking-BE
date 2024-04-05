package com.parkingcomestrue.review.service;

import com.parkingcomestrue.support.Association;
import com.parkingcomestrue.member.domain.Member;
import com.parkingcomestrue.parking.domain.Parking;
import com.parkingcomestrue.review.domain.Content;
import com.parkingcomestrue.review.domain.Review;
import com.parkingcomestrue.review.domain.ReviewRepository;
import com.parkingcomestrue.support.exception.DomainException;
import com.parkingcomestrue.support.exception.ExceptionInformation;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ReviewDomainService {

    private final ReviewRepository reviewRepository;

    public void validateDuplicateReview(Association<Parking> parkingId, Association<Member> reviewerId) {
        if (reviewRepository.existsByParkingIdAndReviewerId(parkingId, reviewerId)) {
            throw new DomainException(ExceptionInformation.DUPLICATE_REVIEW);
        }
    }

    public Map<Content, Long> collectByContent(List<Review> reviews) {
        return reviews.stream()
                .map(Review::getContents)
                .flatMap(Collection::stream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    public int calculateTotalReviews(Map<Content, Long> counts) {
        return counts.values().stream()
                .mapToInt(Long::intValue)
                .sum();
    }
}
