package com.parkingcomestrue.external.support.exception;

import lombok.Getter;

@Getter
public enum SchedulerExceptionInformation {

    INVALID_CONNECT("주차장 API 연결 중 예외 발생"),
    COORDINATE_EXCEPTION("좌표 변환 중 예외 발생");

    private final String message;

    SchedulerExceptionInformation(String message) {
        this.message = message;
    }
}
