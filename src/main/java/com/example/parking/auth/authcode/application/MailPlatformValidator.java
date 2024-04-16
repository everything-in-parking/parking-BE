package com.example.parking.auth.authcode.application;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MailPlatformValidator implements PlatformValidator {

    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private static final Pattern PATTERN = Pattern.compile(EMAIL_PATTERN);

    @Override
    public boolean isInvalidForm(String destination) {
        Matcher matcher = PATTERN.matcher(destination);
        return !matcher.matches();
    }
}
