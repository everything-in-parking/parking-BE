package com.parkingcomestrue.auth.authcode.infrastructure;

public interface AuthCodeSender {

    void send(String destination, String authCode);
}
