package com.parkingcomestrue.member.domain;

import com.parkingcomestrue.support.exception.DomainException;
import com.parkingcomestrue.support.exception.ExceptionInformation;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@Entity
//@SQLRestriction(value = "deleted = false")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String nickname;

    @Embedded
    private Password password;

    private Boolean deleted = Boolean.FALSE;

    public Member(String email, String nickname, Password password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }

    public boolean checkPassword(String password) {
        return this.password.isMatch(password);
    }

    public void delete() {
        deleted = Boolean.TRUE;
    }

    public void changePassword(String previousPassword, String newPassword) {
        if (checkPassword(previousPassword)) {
            this.password = new Password(newPassword);
            return;
        }
        throw new DomainException(ExceptionInformation.INVALID_PASSWORD);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Member member = (Member) o;
        return Objects.equals(getId(), member.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
