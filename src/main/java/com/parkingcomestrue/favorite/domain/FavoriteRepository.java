package com.parkingcomestrue.favorite.domain;

import com.parkingcomestrue.member.domain.Member;
import com.parkingcomestrue.parking.domain.Parking;
import com.parkingcomestrue.support.Association;
import java.util.List;
import org.springframework.data.repository.Repository;

public interface FavoriteRepository extends Repository<Favorite, Long> {

    Favorite save(Favorite favorite);

    void deleteByMemberIdAndParkingId(Association<Member> memberId, Association<Parking> parkingId);

    List<Favorite> findByMemberId(Association<Member> memberId);
}
