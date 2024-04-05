package com.parkingcomestrue.member.domain;

import com.parkingcomestrue.support.exception.DomainException;
import com.parkingcomestrue.support.exception.ExceptionInformation;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface MemberRepository extends Repository<Member, Long> {

    boolean existsByEmail(String email);

    Optional<Member> findById(Long id);

    Optional<Member> findByEmail(String email);

    void save(Member member);

    default Member getById(Long id) {
        return findById(id).orElseThrow(() -> new DomainException(ExceptionInformation.INVALID_MEMBER));
    }
}
