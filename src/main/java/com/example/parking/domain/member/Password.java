package com.example.parking.domain.member;

import com.example.parking.util.cipher.SHA256;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Password {

    private String password;

    public Password(String password) {
        this.password = SHA256.encrypt(password);
    }

    public boolean isMatch(String password) {
        return this.password.equals(SHA256.encrypt(password));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password that = (Password) o;
        return Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }
}
