package com.example.parking.auth.authcode;

import java.util.Arrays;

public enum AuthCodePlatform {

    MAIL("mail");

    private final String platform;

    AuthCodePlatform(String platform) {
        this.platform = platform;
    }

    public static AuthCodePlatform find(String authType) {
        return Arrays.stream(AuthCodePlatform.values())
                .filter(authCodeType -> authCodeType.platform.equals(authType))
                .findAny()
                .orElseThrow(() -> new InValidAuthCodeException("존재하지 않는 인증 코드 전송 플랫폼입니다."));
    }

    public String getPlatform() {
        return platform;
    }
}
