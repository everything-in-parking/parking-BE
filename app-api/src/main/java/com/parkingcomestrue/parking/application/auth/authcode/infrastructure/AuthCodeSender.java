package com.parkingcomestrue.parking.application.auth.authcode.infrastructure;

public interface AuthCodeSender {

    void send(String destination, String authCode);
}
