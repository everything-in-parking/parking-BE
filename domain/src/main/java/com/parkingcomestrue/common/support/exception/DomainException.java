package com.parkingcomestrue.common.support.exception;

public class DomainException extends RuntimeException {

    private final DomainExceptionInformation exceptionInformation;

    public DomainException(DomainExceptionInformation exceptionInformation) {
        super();
        this.exceptionInformation = exceptionInformation;
    }

    @Override
    public String getMessage() {
        return exceptionInformation.getMessage();
    }
}
