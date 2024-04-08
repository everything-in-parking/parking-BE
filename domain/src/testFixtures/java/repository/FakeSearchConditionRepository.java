package repository;

import com.parkingcomestrue.parking.domain.searchcondition.SearchCondition;
import com.parkingcomestrue.parking.domain.searchcondition.repository.SearchConditionRepository;
import java.util.Optional;

public class FakeSearchConditionRepository implements SearchConditionRepository {

    @Override
    public Optional<SearchCondition> findByMemberId(Long memberId) {
        return Optional.empty();
    }

    @Override
    public void save(SearchCondition searchCondition) {

    }
}
