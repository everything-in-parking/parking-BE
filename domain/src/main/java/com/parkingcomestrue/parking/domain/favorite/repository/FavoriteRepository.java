package com.parkingcomestrue.parking.domain.favorite.repository;

import com.parkingcomestrue.parking.domain.favorite.Favorite;
import com.parkingcomestrue.parking.domain.member.Member;
import com.parkingcomestrue.parking.domain.parking.Parking;
import com.parkingcomestrue.parking.support.Association;
import java.util.List;
import org.springframework.data.repository.Repository;

public interface FavoriteRepository extends Repository<Favorite, Long> {

    Favorite save(Favorite favorite);

    void deleteByMemberIdAndParkingId(Association<Member> memberId, Association<Parking> parkingId);

    List<Favorite> findByMemberId(Association<Member> memberId);
}
