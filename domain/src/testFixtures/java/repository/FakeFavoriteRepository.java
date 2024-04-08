package repository;

import com.parkingcomestrue.parking.domain.favorite.Favorite;
import com.parkingcomestrue.parking.domain.favorite.repository.FavoriteRepository;
import com.parkingcomestrue.parking.domain.member.Member;
import com.parkingcomestrue.parking.domain.parking.Parking;
import com.parkingcomestrue.parking.support.Association;
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
