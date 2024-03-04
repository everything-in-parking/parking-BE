package com.example.parking.fake;

import com.example.parking.support.Association;
import com.example.parking.domain.review.Review;
import com.example.parking.domain.review.ReviewRepository;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class BasicReviewRepository implements ReviewRepository, BasicRepository<Review, Long> {

    private static Long id = 1L;
    private final Map<Long, Review> store = new HashMap<>();

    @Override
    public Optional<Review> findById(Long id) {
        return Optional.of(store.get(id));
    }

    @Override
    public List<Review> findAllByParkingId(Association parkingId) {
        return store.values().stream()
                .filter(review -> review.getParkingId().equals(parkingId))
                .toList();
    }

    @Override
    public void save(Review review) {
        setId(review, id);
        store.put(id++, review);
    }

    @Override
    public boolean existsByParkingIdAndReviewerId(Association parkingId, Association reviewerId) {
        return store.values().stream()
                .anyMatch(
                        review -> review.getParkingId().equals(parkingId) &&
                                review.getReviewerId().equals(reviewerId)
                );
    }
}
