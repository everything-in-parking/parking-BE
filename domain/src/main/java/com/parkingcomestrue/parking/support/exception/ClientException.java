package com.parkingcomestrue.parking.support.exception;

import lombok.Getter;

@Getter
public class ClientException extends RuntimeException {

    private final ExceptionInformation exceptionInformation;

    public ClientException(ExceptionInformation exceptionInformation) {
        super();
        this.exceptionInformation = exceptionInformation;
    }

    @Override
    public String getMessage() {
        return exceptionInformation.getMessage();
    }
}
