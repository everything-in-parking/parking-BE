package com.parkingcomestrue.parking.application.exception;

import lombok.Getter;

@Getter
public enum ClientExceptionInformation {

    DUPLICATE_MAIL("중복된 이메일이라 회원가입이 불가능합니다."),
    INVALID_EMAIL("회원가입되지 않은 이메일입니다."),
    UNAUTHORIZED("존재하지 않는 sessionId 입니다."),

    INVALID_AUTH_CODE("존재하지 않는 인증코드 입니다."),

    INVALID_DESCRIPTION("해당하는 내용의 검색 조건이 존재하지 않습니다.");


    private final String message;

    ClientExceptionInformation(String message) {
        this.message = message;
    }
}
