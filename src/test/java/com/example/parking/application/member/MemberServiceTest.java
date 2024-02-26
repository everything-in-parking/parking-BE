package com.example.parking.application.member;

import com.example.parking.application.member.dto.MemberLoginRequest;
import com.example.parking.application.member.dto.MemberNotFoundException;
import com.example.parking.application.member.dto.MemberSignupRequest;
import com.example.parking.application.member.dto.PasswordChangeRequest;
import com.example.parking.application.member.exception.MemberLoginException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Test
    void 비밀번호를_바꾸면_바꾼_비밀번호로_로그인이_가능하다() {
        // given
        String email = "email@google.com";
        String previousPassword = "password";
        MemberSignupRequest memberSignupRequest = new MemberSignupRequest("name", email, previousPassword, "nickname");
        Long memberId = memberService.signup(memberSignupRequest);

        String newPassword = "newPassword";

        // when
        memberService.changePassword(memberId, new PasswordChangeRequest(previousPassword, newPassword));

        // then
        Assertions.assertThatNoException()
                .isThrownBy(() -> memberService.login(new MemberLoginRequest(email, newPassword)));
    }

    @Test
    void 비밀번호를_바꾸면_이전의_비밀번호로_로그인이_불가능하다() {
        // given
        String email = "email@google.com";
        String previousPassword = "password";
        MemberSignupRequest memberSignupRequest = new MemberSignupRequest("name", email, previousPassword, "nickname");
        Long memberId = memberService.signup(memberSignupRequest);

        String newPassword = "newPassword";

        // when
        memberService.changePassword(memberId, new PasswordChangeRequest(previousPassword, newPassword));

        // then
        Assertions.assertThatThrownBy(() -> memberService.login(new MemberLoginRequest(email, previousPassword)))
                .isInstanceOf(MemberLoginException.class)
                .hasMessage("비밀번호가 틀립니다.");
    }

    @Test
    void 비밀번호를_바꿀_때_해당_회원이_존재하지_않으면_예외를_던진다() {
        // given
        String previousPassword = "password";
        String newPassword = "newPassword";
        Long wrongMemberId = 12312541L;

        // when, then
        Assertions.assertThatThrownBy(() -> memberService.changePassword(wrongMemberId, new PasswordChangeRequest(previousPassword, newPassword)))
                .isInstanceOf(MemberNotFoundException.class)
                .hasMessage("존재하지 않는 회원입니다.");
    }
}
