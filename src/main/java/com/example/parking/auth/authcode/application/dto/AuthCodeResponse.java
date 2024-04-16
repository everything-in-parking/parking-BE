package com.example.parking.auth.authcode.application.dto;

import lombok.Getter;

@Getter
public class AuthCodeResponse {

    private String authCode;

    public AuthCodeResponse(String authCode) {
        this.authCode = authCode;
    }
}
