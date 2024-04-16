package com.example.parking.domain.review;

import com.example.parking.support.Association;
import com.example.parking.domain.member.Member;
import com.example.parking.domain.parking.Parking;
import com.example.parking.support.exception.DomainException;
import com.example.parking.support.exception.ExceptionInformation;
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
