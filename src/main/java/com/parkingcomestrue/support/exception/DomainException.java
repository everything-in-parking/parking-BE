package com.parkingcomestrue.support.exception;

public class DomainException extends RuntimeException{

    private final ExceptionInformation exceptionInformation;

    public DomainException(ExceptionInformation exceptionInformation) {
        super();
        this.exceptionInformation = exceptionInformation;
    }

    @Override
    public String getMessage() {
        return exceptionInformation.getMessage();
    }
}
