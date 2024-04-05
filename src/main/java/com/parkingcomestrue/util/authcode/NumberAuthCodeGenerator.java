package com.parkingcomestrue.util.authcode;

import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class NumberAuthCodeGenerator implements AuthCodeGenerator {

    private static final int MAX_LENGTH = 6;
    private static final Random random = new Random();

    @Override
    public String generateAuthCode() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < MAX_LENGTH; i++) {
            stringBuilder.append(random.nextInt(9));
        }
        return stringBuilder.toString();
    }
}
