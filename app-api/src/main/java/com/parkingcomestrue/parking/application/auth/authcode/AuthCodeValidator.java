package com.parkingcomestrue.parking.application.auth.authcode;

import java.util.EnumMap;
import org.springframework.stereotype.Component;

@Component
public class AuthCodeValidator {

    private final EnumMap<AuthCodePlatform, PlatformValidator> validators = new EnumMap<>(AuthCodePlatform.class);

    public AuthCodeValidator() {
        validators.put(AuthCodePlatform.MAIL, new MailPlatformValidator());
    }

    public void validate(AuthCodePlatform authCodePlatform, String destination) {
        PlatformValidator platformValidator = validators.get(authCodePlatform);
        if (platformValidator.isInvalidForm(destination)) {
            throw new InValidAuthCodeException("인증 코드 플랫폼과 수신지 형식이 일치하지 않습니다.");
        }
    }
}
