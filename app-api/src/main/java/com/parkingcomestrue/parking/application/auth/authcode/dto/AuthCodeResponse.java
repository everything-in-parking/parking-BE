package com.parkingcomestrue.parking.application.auth.authcode.dto;

import lombok.Getter;

@Getter
public class AuthCodeResponse {

    private String authCode;

    public AuthCodeResponse(String authCode) {
        this.authCode = authCode;
    }
}
