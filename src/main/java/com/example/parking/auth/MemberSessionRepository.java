package com.example.parking.auth;

import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberSessionRepository extends JpaRepository<MemberSession, String> {

    Optional<MemberSession> findBySessionIdAndExpiredAtGreaterThanEqual(String sessionId, LocalDateTime expiredAt);
}
