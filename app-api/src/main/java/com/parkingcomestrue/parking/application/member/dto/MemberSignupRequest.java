package com.parkingcomestrue.parking.application.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberSignupRequest {

    private String name;
    private String email;
    private String password;
    private String nickname;

    public MemberSignupRequest(String name, String email, String password, String nickname) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}
