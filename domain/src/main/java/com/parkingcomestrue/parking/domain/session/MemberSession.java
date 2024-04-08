package com.parkingcomestrue.parking.domain.session;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSession {

    @Id
    private String sessionId;
    private Long memberId;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;

    public MemberSession(String sessionId, Long memberId, LocalDateTime createdAt, LocalDateTime expiredAt) {
        this.sessionId = sessionId;
        this.memberId = memberId;
        this.createdAt = createdAt;
        this.expiredAt = expiredAt;
    }

    public void updateExpiredAt(LocalDateTime newExpiredAt) {
        expiredAt = newExpiredAt;
    }
}
