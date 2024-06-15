package com.parkingcomestrue.common.domain.searchcondition.repository;

import com.parkingcomestrue.common.domain.member.Member;
import com.parkingcomestrue.common.domain.searchcondition.SearchCondition;
import com.parkingcomestrue.common.support.Association;
import com.parkingcomestrue.common.support.exception.DomainException;
import com.parkingcomestrue.common.support.exception.DomainExceptionInformation;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface SearchConditionRepository extends Repository<SearchCondition, Long> {

    Optional<SearchCondition> findByMemberId(Association<Member> memberId);

    default SearchCondition getByMemberId(Association<Member> memberId) {
        return findByMemberId(memberId)
                .orElseThrow(() -> new DomainException(DomainExceptionInformation.INVALID_SEARCH_CONDITION));
    }

    void save(SearchCondition searchCondition);
}
