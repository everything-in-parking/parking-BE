package com.example.parking.application.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberLoginRequest {

    private String email;
    private String password;

    public MemberLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
