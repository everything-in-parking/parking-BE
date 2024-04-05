package com.parkingcomestrue.config;

import com.parkingcomestrue.auth.authcode.infrastructure.AuthCodeSender;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public AuthCodeSender fakeAuthCodeSender() {
        return new AuthCodeSender() {
            @Override
            public void send(String destination, String authCode) {

            }
        };
    }
}
