package com.example.parking.domain.member;

import java.util.Optional;
import org.springframework.data.repository.Repository;

public interface MemberRepository extends Repository<Member, Long> {

    boolean existsByEmail(String email);

    Optional<Member> findById(Long id);

    Optional<Member> findByEmail(String email);

    void save(Member member);

    default Member getById(Long id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));
    }
}
