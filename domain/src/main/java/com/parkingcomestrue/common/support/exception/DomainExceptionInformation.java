package com.parkingcomestrue.common.support.exception;

import lombok.Getter;

@Getter
public enum DomainExceptionInformation {

    INVALID_MEMBER("존재하지 않는 회원입니다."),
    INVALID_PASSWORD("비밀번호가 틀립니다."),

    INVALID_PARKING("존재하지 않는 주차장입니다."),
    INVALID_CONTENT("존재하지 않는 리뷰 내용입니다."),
    INVALID_REVIEW("존재하지 않는 리뷰입니다."),
    DUPLICATE_REVIEW("유저가 해당 주차장에 대해 이미 리뷰를 작성하였습니다."),
    INVALID_CONTENTS_SIZE("리뷰 내용은 1개에서 3개까지 선택가능합니다."),
    INVALID_HOURS("이용 시간은 1~12, 24 시간까지만 선택할 수 있습니다."),
    INVALID_SEARCH_CONDITION("해당 회원의 검색 조건이 존재하지 않습니다."),

    ENCRYPT_EXCEPTION("암호화에 실패했습니다."),

    INVALID_LOCATION("경도 또는 위도가 올바르지 않습니다.");

    private final String message;

    DomainExceptionInformation(String message) {
        this.message = message;
    }
}
