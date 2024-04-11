package com.parkingcomestrue.parking.domain.review.repository;

import com.parkingcomestrue.parking.domain.member.Member;
import com.parkingcomestrue.parking.domain.parking.Parking;
import com.parkingcomestrue.parking.domain.review.Review;
import com.parkingcomestrue.parking.support.Association;
import com.parkingcomestrue.parking.support.exception.DomainException;
import com.parkingcomestrue.parking.support.exception.DomainExceptionInformation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface ReviewRepository extends Repository<Review, Long> {

    Optional<Review> findById(Long id);

    default Review getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new DomainException(DomainExceptionInformation.INVALID_REVIEW));
    }

    List<Review> findAllByParkingId(Association<Parking> parkingId);

    void save(Review review);

    boolean existsByParkingIdAndReviewerId(Association<Parking> parkingId, Association<Member> reviewerId);
}
