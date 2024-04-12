package com.parkingcomestrue.external.support.exception;

public class SchedulerException extends RuntimeException {

    private final SchedulerExceptionInformation exceptionInformation;

    public SchedulerException(SchedulerExceptionInformation exceptionInformation) {
        super();
        this.exceptionInformation = exceptionInformation;
    }

    @Override
    public String getMessage() {
        return exceptionInformation.getMessage();
    }
}
