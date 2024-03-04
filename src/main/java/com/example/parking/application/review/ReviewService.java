package com.example.parking.application.review;

import com.example.parking.application.review.dto.ReviewCreateRequest;
import com.example.parking.domain.member.Member;
import com.example.parking.domain.member.MemberRepository;
import com.example.parking.domain.parking.Parking;
import com.example.parking.domain.parking.ParkingRepository;
import com.example.parking.domain.review.Review;
import com.example.parking.domain.review.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ParkingRepository parkingRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public Long createReview(Long parkingId, Long reviewerId, ReviewCreateRequest request) {
        Parking parking = parkingRepository.getById(parkingId);
        Member reviewer = memberRepository.getById(reviewerId);
        if (reviewRepository.existsByParkingAndReviewer(parking, reviewer)) {
            throw new IllegalStateException("유저가 해당 주차장에 대해 이미 리뷰를 작성하였습니다.");
        }

        Review review = new Review(parking, reviewer, request.toContents());
        reviewRepository.save(review);
        return review.getId();
    }
}
