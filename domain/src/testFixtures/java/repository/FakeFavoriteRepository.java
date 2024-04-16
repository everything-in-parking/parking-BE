package repository;

import com.parkingcomestrue.common.domain.favorite.Favorite;
import com.parkingcomestrue.common.domain.favorite.repository.FavoriteRepository;
import com.parkingcomestrue.common.domain.member.Member;
import com.parkingcomestrue.common.domain.parking.Parking;
import com.parkingcomestrue.common.support.Association;
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
