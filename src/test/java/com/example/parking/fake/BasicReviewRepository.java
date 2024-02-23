package com.example.parking.fake;

import com.example.parking.domain.member.Member;
import com.example.parking.domain.parking.Parking;
import com.example.parking.domain.review.Review;
import com.example.parking.domain.review.ReviewRepository;
import java.util.HashMap;
import java.util.Map;

public class BasicReviewRepository implements ReviewRepository, BasicRepository<Review, Long> {

    private static Long id = 1L;
    private final Map<Long, Review> store = new HashMap<>();

    @Override
    public void save(Review review) {
        setId(review, id);
        store.put(id++, review);
    }

    @Override
    public boolean existsByParkingAndReviewer(Parking parking, Member reviewer) {
        return store.values().stream()
                .anyMatch(review -> review.getParking().equals(parking) && review.getReviewer().equals(reviewer));
    }
}
