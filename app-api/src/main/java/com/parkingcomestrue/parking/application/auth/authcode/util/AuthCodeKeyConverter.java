
package com.parkingcomestrue.parking.application.auth.authcode.util;

import java.util.StringJoiner;

public class AuthCodeKeyConverter {

    public static String convert(String randomAuthCode, String destination, String authCodePlatform,
                                 String authCodeCategory) {
        StringJoiner stringJoiner = new StringJoiner(":");
        return stringJoiner
                .add(randomAuthCode)
                .add(destination)
                .add(authCodePlatform)
                .add(authCodeCategory).toString();
    }
}
