package com.example.parking.domain.searchcondition;

import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface SearchConditionRepository extends Repository<SearchCondition, Long> {

    Optional<SearchCondition> findByMemberId(Long memberId);

    default SearchCondition getByMemberId(Long memberId) {
        return findByMemberId(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 회원의 검색 조건이 존재하지 않습니다."));
    }

    void save(SearchCondition searchCondition);
}
