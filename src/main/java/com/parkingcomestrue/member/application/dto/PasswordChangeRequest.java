package com.parkingcomestrue.member.application.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PasswordChangeRequest {

    private String previousPassword;
    private String newPassword;

    public PasswordChangeRequest(String previousPassword, String newPassword) {
        this.previousPassword = previousPassword;
        this.newPassword = newPassword;
    }
}
