package com.parkingcomestrue.common.domain.favorite.repository;

import com.parkingcomestrue.common.domain.favorite.Favorite;
import com.parkingcomestrue.common.domain.member.Member;
import com.parkingcomestrue.common.domain.parking.Parking;
import com.parkingcomestrue.common.support.Association;
import java.util.List;
import org.springframework.data.repository.Repository;

public interface FavoriteRepository extends Repository<Favorite, Long> {

    Favorite save(Favorite favorite);

    void deleteByMemberIdAndParkingId(Association<Member> memberId, Association<Parking> parkingId);

    List<Favorite> findByMemberId(Association<Member> memberId);
}
