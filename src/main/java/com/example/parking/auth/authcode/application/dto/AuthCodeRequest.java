package com.example.parking.auth.authcode.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthCodeRequest {

    private String destination;
    private String authPlatform;
    private String authCodeCategory;

    public AuthCodeRequest(String destination, String authPlatform, String authCodeCategory) {
        this.destination = destination;
        this.authPlatform = authPlatform;
        this.authCodeCategory = authCodeCategory;
    }
}
