package com.example.parking.domain.review;

import com.example.parking.domain.common.Association;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface ReviewRepository extends Repository<Review, Long> {

    Optional<Review> findById(Long id);

    default Review getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 review 입니다."));
    }

    void save(Review review);

    boolean existsByParkingIdAndReviewerId(Association parkingId, Association reviewerId);
}
