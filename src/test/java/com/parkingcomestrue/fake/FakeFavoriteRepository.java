package com.parkingcomestrue.fake;

import com.parkingcomestrue.favorite.domain.Favorite;
import com.parkingcomestrue.favorite.domain.FavoriteRepository;
import com.parkingcomestrue.member.domain.Member;
import com.parkingcomestrue.parking.domain.Parking;
import com.parkingcomestrue.support.Association;
import java.util.List;

public class FakeFavoriteRepository implements FavoriteRepository {

    @Override
    public Favorite save(Favorite favorite) {
        return null;
    }

    @Override
    public void deleteByMemberIdAndParkingId(Association<Member> memberId, Association<Parking> parkingId) {

    }

    @Override
    public List<Favorite> findByMemberId(Association<Member> memberId) {
        return null;
    }
}
