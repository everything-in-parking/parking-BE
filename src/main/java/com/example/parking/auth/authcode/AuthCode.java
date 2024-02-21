package com.example.parking.auth.authcode;

import com.example.parking.domain.AuditingEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class AuthCode extends AuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String destination;

    private String authCode;

    @Enumerated(EnumType.STRING)
    private AuthCodePlatform authCodePlatform;

    @Enumerated(EnumType.STRING)
    private AuthCodeCategory authCodeCategory;

    private Boolean isUsed = Boolean.FALSE;

    public AuthCode(String destination, String authCode, AuthCodePlatform authCodePlatform,
                    AuthCodeCategory authCodeCategory) {
        this.destination = destination;
        this.authCode = authCode;
        this.authCodePlatform = authCodePlatform;
        this.authCodeCategory = authCodeCategory;
    }

    public boolean isMatchAuthCode(String authCode) {
        return this.authCode.equals(authCode);
    }

    public void certificate(String authCode) {
        if (!this.authCode.equals(authCode) || this.isUsed) {
            throw new IllegalArgumentException("");
        }
        this.isUsed = Boolean.TRUE;
    }
}
