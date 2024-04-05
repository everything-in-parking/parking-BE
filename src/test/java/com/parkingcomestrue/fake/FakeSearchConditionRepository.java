package com.parkingcomestrue.fake;

import com.parkingcomestrue.searchcondition.domain.SearchCondition;
import com.parkingcomestrue.searchcondition.domain.SearchConditionRepository;
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
