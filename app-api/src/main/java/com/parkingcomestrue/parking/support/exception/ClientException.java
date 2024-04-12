package com.parkingcomestrue.parking.support.exception;

import lombok.Getter;

@Getter
public class ClientException extends RuntimeException {

    private final ClientExceptionInformation exceptionInformation;

    public ClientException(ClientExceptionInformation exceptionInformation) {
        super();
        this.exceptionInformation = exceptionInformation;
    }

    @Override
    public String getMessage() {
        return exceptionInformation.getMessage();
    }
}
