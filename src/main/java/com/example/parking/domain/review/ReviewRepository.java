package com.example.parking.domain.review;

import com.example.parking.domain.member.Member;
import com.example.parking.domain.parking.Parking;
import org.springframework.data.repository.Repository;

public interface ReviewRepository extends Repository<Review, Long> {

    void save(Review review);

    boolean existsByParkingAndReviewer(Parking parking, Member reviewer);
}
