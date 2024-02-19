package com.example.parking.domain.member;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class MemberTest {

    @Test
    void 비밀번호가_일치하면_true_를_반환한다() {
        // given
        String plainPassword = "password";
        Password password = new Password(plainPassword);
        Member member = new Member("name", "email@google.com", "nickname", password);

        // when, then
        Assertions.assertThat(member.checkPassword(plainPassword)).isTrue();
    }

    @Test
    void 비밀번호가_일치하지_않으면_false_를_반환한다() {
        // given
        String plainPassword = "password";
        Password password = new Password(plainPassword);
        Member member = new Member("name", "email@google.com", "nickname", password);
        String wrongPlainPassword = "wrongPassword";

        // when, then
        Assertions.assertThat(member.checkPassword(wrongPlainPassword)).isFalse();
    }

    @Test
    void 비밀번호를_바꾼다() {
        // given
        String previousPassword = "password";
        Password password = new Password(previousPassword);
        Member member = new Member("name", "email@google.com", "nickname", password);
        String newPassword = "newPassword";

        // when
        member.changePassword(previousPassword, newPassword);

        // then
        Assertions.assertThat(member.checkPassword(newPassword)).isTrue();
    }

    @Test
    void 비밀번호를_바꿀_때_이전의_비밀번호가_일치하지_않으면_예외를_던진다() {
        // given
        String previousPassword = "password";
        Password password = new Password(previousPassword);
        Member member = new Member("name", "email@google.com", "nickname", password);
        String newPassword = "newPassword";

        String wrongPreviousPassword = "wrongPassword";

        // when, then
        Assertions.assertThatThrownBy(() -> member.changePassword(wrongPreviousPassword, newPassword))
                .isInstanceOf(PasswordNotMatchedException.class)
                .hasMessage("비밀번호가 맞지 않습니다.");
    }
}
