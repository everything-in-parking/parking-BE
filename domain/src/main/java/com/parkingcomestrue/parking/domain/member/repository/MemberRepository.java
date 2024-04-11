package com.parkingcomestrue.parking.domain.member.repository;

import com.parkingcomestrue.parking.domain.member.Member;
import com.parkingcomestrue.parking.support.exception.DomainException;
import com.parkingcomestrue.parking.support.exception.DomainExceptionInformation;
import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface MemberRepository extends Repository<Member, Long> {

    boolean existsByEmail(String email);

    Optional<Member> findById(Long id);

    Optional<Member> findByEmail(String email);

    void save(Member member);

    default Member getById(Long id) {
        return findById(id).orElseThrow(() -> new DomainException(DomainExceptionInformation.INVALID_MEMBER));
    }
}
