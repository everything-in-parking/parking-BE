package com.parkingcomestrue.parking.domain.session.repository;

import com.parkingcomestrue.parking.domain.session.MemberSession;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberSessionRepository extends JpaRepository<MemberSession, String> {

    Optional<MemberSession> findBySessionIdAndExpiredAtIsGreaterThanEqual(String sessionId, LocalDateTime expiredAt);
}
