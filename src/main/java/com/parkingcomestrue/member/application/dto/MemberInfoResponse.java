package com.parkingcomestrue.member.application.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MemberInfoResponse {

    private String name;
    private String email;

    public MemberInfoResponse(String name, String email) {
        this.name = name;
        this.email = email;
    }
}
