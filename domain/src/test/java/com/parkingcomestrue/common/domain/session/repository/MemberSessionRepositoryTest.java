package com.parkingcomestrue.common.domain.session.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.parkingcomestrue.common.domain.session.MemberSession;
import com.parkingcomestrue.common.domain.session.repository.MemberSessionRepository;
import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberSessionRepositoryTest {

    @Autowired
    private MemberSessionRepository memberSessionRepository;

    @Test
    void 현재_시간이_세션_만료_시간보다_늦지_않으면_세션이_조회된다() {
        // given
        MemberSession memberSession = new MemberSession(UUID.randomUUID().toString(), 1L, LocalDateTime.now(),
                LocalDateTime.now());
        memberSessionRepository.save(memberSession);

        // when, then
        assertThat(memberSessionRepository.findBySessionIdAndExpiredAtIsGreaterThanEqual(memberSession.getSessionId(),
                LocalDateTime.now().minusDays(1)))
                .isPresent();
    }

    @Test
    void 현재_시간이_세션_만료_시간보다_늦으면_빈_옵셔널을_반환한다() {
        // given
        MemberSession memberSession = new MemberSession(UUID.randomUUID().toString(), 1L, LocalDateTime.now(),
                LocalDateTime.now().minusDays(1));
        memberSessionRepository.save(memberSession);

        // when, then
        assertThat(memberSessionRepository.findBySessionIdAndExpiredAtIsGreaterThanEqual(memberSession.getSessionId(),
                LocalDateTime.now()))
                .isEmpty();
    }
}
