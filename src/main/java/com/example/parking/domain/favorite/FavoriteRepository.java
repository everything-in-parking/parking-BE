package com.example.parking.domain.favorite;

import com.example.parking.domain.member.Member;
import com.example.parking.domain.parking.Parking;
import com.example.parking.support.Association;
import java.util.List;
import org.springframework.data.repository.Repository;

public interface FavoriteRepository extends Repository<Favorite, Long> {

    Favorite save(Favorite favorite);

    void deleteByMemberIdAndParkingId(Association<Member> memberId, Association<Parking> parkingId);

    List<Favorite> findByMemberId(Association<Member> memberId);
}
