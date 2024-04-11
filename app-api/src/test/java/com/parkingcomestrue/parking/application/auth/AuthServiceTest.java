package com.parkingcomestrue.parking.application.auth;

import static com.parkingcomestrue.parking.application.exception.ClientExceptionInformation.INVALID_AUTH_CODE;
import static com.parkingcomestrue.parking.application.exception.ClientExceptionInformation.UNAUTHORIZED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import com.parkingcomestrue.parking.application.container.ContainerTest;
import com.parkingcomestrue.parking.application.auth.authcode.AuthCodeCategory;
import com.parkingcomestrue.parking.application.auth.authcode.AuthCodePlatform;
import com.parkingcomestrue.parking.application.auth.authcode.dto.AuthCodeCertificateRequest;
import com.parkingcomestrue.parking.application.auth.authcode.dto.AuthCodeRequest;
import com.parkingcomestrue.parking.domain.session.MemberSession;
import com.parkingcomestrue.parking.application.exception.ClientException;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;

class AuthServiceTest extends ContainerTest {

    private static final String AUTH_CODE = "111111";


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
                .isInstanceOf(ClientException.class)
                .hasMessage(UNAUTHORIZED.getMessage());
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

    @Test
    void 가장_최근에_발급받은_인증번호는_검증가능하다() {
        // given
        String authCodeDestination = "destination@gmail.com";
        AuthCodePlatform authCodePlatform = AuthCodePlatform.MAIL;
        AuthCodeCategory authCodeCategory = AuthCodeCategory.SIGN_UP;

        String oldAuthCode = authService.createAuthCode(
                new AuthCodeRequest(authCodeDestination, authCodePlatform.getPlatform(),
                        authCodeCategory.getCategoryName())
        );

        String newAuthCode = authService.createAuthCode(
                new AuthCodeRequest(authCodeDestination, authCodePlatform.getPlatform(),
                        authCodeCategory.getCategoryName())
        );

        // when
        AuthCodeCertificateRequest authCodeCertificateRequest = new AuthCodeCertificateRequest(
                authCodeDestination,
                authCodePlatform.getPlatform(),
                AuthCodeCategory.SIGN_UP.getCategoryName(),
                newAuthCode
        );

        // then
        assertDoesNotThrow(() -> authService.certificateAuthCode(authCodeCertificateRequest));
    }

    @Test
    void 이미_인증받은_인증번호는_다시_사용할_수_없다() {
        // given
        String authCodeDestination = "destination@gmail.com";
        AuthCodePlatform authCodePlatform = AuthCodePlatform.MAIL;
        AuthCodeCategory authCodeCategory = AuthCodeCategory.SIGN_UP;

        String oldAuthCode = authService.createAuthCode(
                new AuthCodeRequest(authCodeDestination, authCodePlatform.getPlatform(),
                        authCodeCategory.getCategoryName())
        );

        // when
        AuthCodeCertificateRequest authCodeCertificateRequest = new AuthCodeCertificateRequest(
                authCodeDestination,
                authCodePlatform.getPlatform(),
                AuthCodeCategory.SIGN_UP.getCategoryName(),
                oldAuthCode
        );
        authService.certificateAuthCode(authCodeCertificateRequest);

        // then (인증받은 인증코드로 인증 다시 시도)
        assertThatThrownBy(() -> authService.certificateAuthCode(authCodeCertificateRequest))
                .isInstanceOf(ClientException.class)
                .hasMessage(INVALID_AUTH_CODE.getMessage());
    }

    @Test
    void 가장_최근의_인증번호가_아니어도_인증_가능하다() {
        // given
        String authCodeDestination = "destination@gmail.com";
        AuthCodePlatform authCodePlatform = AuthCodePlatform.MAIL;
        AuthCodeCategory authCodeCategory = AuthCodeCategory.SIGN_UP;

        String oldAuthCode = authService.createAuthCode(
                new AuthCodeRequest(authCodeDestination, authCodePlatform.getPlatform(),
                        authCodeCategory.getCategoryName())
        );

        String newAuthCode = authService.createAuthCode(
                new AuthCodeRequest(authCodeDestination, authCodePlatform.getPlatform(),
                        authCodeCategory.getCategoryName())
        );

        // when
        AuthCodeCertificateRequest authCodeCertificateRequest = new AuthCodeCertificateRequest(
                authCodeDestination,
                authCodePlatform.getPlatform(),
                AuthCodeCategory.SIGN_UP.getCategoryName(),
                oldAuthCode
        );

        // then (예전 인증코드(oldAuthCode)로 인증 시도)
        assertDoesNotThrow(() -> authService.certificateAuthCode(authCodeCertificateRequest));
    }
}
