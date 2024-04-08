package com.parkingcomestrue.parking.support.exception;

import lombok.Getter;

@Getter
public enum ExceptionInformation {
    DUPLICATE_MAIL("중복된 이메일이라 회원가입이 불가능합니다."),
    INVALID_EMAIL("회원가입되지 않은 이메일입니다."),
    INVALID_MEMBER("존재하지 않는 회원입니다."),
    INVALID_PASSWORD("비밀번호가 틀립니다."),
    UNAUTHORIZED("존재하지 않는 sessionId 입니다."),

    INVALID_PARKING("존재하지 않는 주차장입니다."),
    INVALID_CONTENT("존재하지 않는 리뷰 내용입니다."),
    INVALID_REVIEW("존재하지 않는 리뷰입니다."),
    DUPLICATE_REVIEW("유저가 해당 주차장에 대해 이미 리뷰를 작성하였습니다."),
    INVALID_CONTENTS_SIZE("리뷰 내용은 1개에서 3개까지 선택가능합니다."),
    INVALID_HOURS("이용 시간은 1~12, 24 시간까지만 선택할 수 있습니다."),
    INVALID_SEARCH_CONDITION("해당 회원의 검색 조건이 존재하지 않습니다."),

    ENCRYPT_EXCEPTION("암호화에 실패했습니다."),

    INVALID_CONNECT("주차장 API 연결 중 예외 발생"),
    COORDINATE_EXCEPTION("좌표 변환 중 예외 발생"),
    INVALID_AUTH_CODE("존재하지 않는 인증코드 입니다."),
    INVALID_DESCRIPTION("해당하는 내용의 검색 조건이 존재하지 않습니다."),
    INVALID_LOCATION("경도 또는 위도가 올바르지 않습니다.");

    private final String message;

    ExceptionInformation(String message) {
        this.message = message;
    }
}
