package com.example.parking.auth;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@Transactional
@SpringBootTest
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Test
    void 세션_아이디에_해당하는_세션을_찾는다() {
        // given
        Long memberId = 1L;
        String sessionId = authService.createSession(memberId);

        // when, then
        assertThatNoException()
                .isThrownBy(() -> authService.findSession(sessionId));
    }

    @Test
    void 세션_아이디에_해당하는_세션이_존재하지_않으면_예외를_던진다() {
        // given
        String wrongSessionId = "아무세션아이디";

        // when, then
        assertThatThrownBy(() -> authService.findSession(wrongSessionId))
                .isInstanceOf(UnAuthorizationException.class);
    }

    @Test
    void 세션_만료시간을_갱신한다() {
        // given
        Long memberId = 1L;
        String sessionId = authService.createSession(memberId);
        MemberSession originSession = authService.findSession(sessionId);
        LocalDateTime originExpiredAt = originSession.getExpiredAt();

        // when
        authService.updateSessionExpiredAt(originSession);
        MemberSession updatedSession = authService.findSession(sessionId);
        LocalDateTime updatedExpiredAt = updatedSession.getExpiredAt();

        // then
        assertThat(updatedExpiredAt).isAfter(originExpiredAt);
    }
}
