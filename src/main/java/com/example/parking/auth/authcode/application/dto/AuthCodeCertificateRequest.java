package com.example.parking.auth.authcode.application.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthCodeCertificateRequest {

    private String destination;
    private String authCodePlatform;
    private String authCodeCategory;
    private String authCode;

    public AuthCodeCertificateRequest(String destination, String authCodePlatform, String authCodeCategory,
                                      String authCode) {
        this.destination = destination;
        this.authCodePlatform = authCodePlatform;
        this.authCodeCategory = authCodeCategory;
        this.authCode = authCode;
    }
}
