package com.parkingcomestrue.parking.domain.review.service;

import com.parkingcomestrue.parking.domain.member.Member;
import com.parkingcomestrue.parking.domain.parking.Parking;
import com.parkingcomestrue.parking.domain.review.Content;
import com.parkingcomestrue.parking.domain.review.Review;
import com.parkingcomestrue.parking.domain.review.repository.ReviewRepository;
import com.parkingcomestrue.parking.support.Association;
import com.parkingcomestrue.parking.support.exception.DomainException;
import com.parkingcomestrue.parking.support.exception.DomainExceptionInformation;
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
            throw new DomainException(DomainExceptionInformation.DUPLICATE_REVIEW);
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
