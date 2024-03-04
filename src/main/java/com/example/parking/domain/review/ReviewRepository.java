package com.example.parking.domain.review;

import com.example.parking.support.Association;
import com.example.parking.domain.member.Member;
import com.example.parking.domain.parking.Parking;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface ReviewRepository extends Repository<Review, Long> {

    Optional<Review> findById(Long id);

    default Review getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 review 입니다."));
    }

    List<Review> findAllByParkingId(Association<Parking> parkingId);

    void save(Review review);

    boolean existsByParkingIdAndReviewerId(Association<Parking> parkingId, Association<Member> reviewerId);
}
