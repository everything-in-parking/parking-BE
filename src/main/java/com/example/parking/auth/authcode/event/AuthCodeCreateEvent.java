package com.example.parking.auth.authcode.event;

import lombok.Getter;

@Getter
public class AuthCodeCreateEvent {

    private final String destination;
    private final String authCode;
    private final String authCodePlatform;
    private final String authCodeCategory;

    public AuthCodeCreateEvent(String destination, String authCode, String authCodePlatform, String authCodeCategory) {
        this.destination = destination;
        this.authCode = authCode;
        this.authCodePlatform = authCodePlatform;
        this.authCodeCategory = authCodeCategory;
    }
}
