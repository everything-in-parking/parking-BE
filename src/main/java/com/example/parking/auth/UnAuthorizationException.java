package com.example.parking.auth;

public class UnAuthorizationException extends RuntimeException {

    private final String sessionId;

    public UnAuthorizationException(String message, String sessionId) {
        super(message);
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }
}
