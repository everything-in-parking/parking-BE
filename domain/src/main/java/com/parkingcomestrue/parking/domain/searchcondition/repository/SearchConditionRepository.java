package com.parkingcomestrue.parking.domain.searchcondition.repository;

import com.parkingcomestrue.parking.domain.searchcondition.SearchCondition;
import com.parkingcomestrue.parking.support.exception.DomainException;
import com.parkingcomestrue.parking.support.exception.ExceptionInformation;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface SearchConditionRepository extends Repository<SearchCondition, Long> {

    Optional<SearchCondition> findByMemberId(Long memberId);

    default SearchCondition getByMemberId(Long memberId) {
        return findByMemberId(memberId)
                .orElseThrow(() -> new DomainException(ExceptionInformation.INVALID_SEARCH_CONDITION));
    }

    void save(SearchCondition searchCondition);
}
