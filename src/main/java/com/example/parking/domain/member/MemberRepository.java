package com.example.parking.domain.member;

import com.example.parking.application.member.dto.MemberNotFoundException;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email);

    Optional<Member> findByEmail(String email);

    default Member getById(Long id) {
        return findById(id).orElseThrow(() -> new MemberNotFoundException("존재하지 않는 회원입니다."));
    }
}
