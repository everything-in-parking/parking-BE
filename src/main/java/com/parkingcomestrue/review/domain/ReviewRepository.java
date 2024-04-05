package com.parkingcomestrue.review.domain;

import com.parkingcomestrue.support.Association;
import com.parkingcomestrue.member.domain.Member;
import com.parkingcomestrue.parking.domain.Parking;
import com.parkingcomestrue.support.exception.DomainException;
import com.parkingcomestrue.support.exception.ExceptionInformation;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface ReviewRepository extends Repository<Review, Long> {

    Optional<Review> findById(Long id);

    default Review getById(Long id) {
        return findById(id)
                .orElseThrow(() -> new DomainException(ExceptionInformation.INVALID_REVIEW));
    }

    List<Review> findAllByParkingId(Association<Parking> parkingId);

    void save(Review review);

    boolean existsByParkingIdAndReviewerId(Association<Parking> parkingId, Association<Member> reviewerId);
}
