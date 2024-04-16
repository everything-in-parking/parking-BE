package com.example.parking.util.cipher;

import com.example.parking.support.exception.DomainException;
import com.example.parking.support.exception.ExceptionInformation;
import java.security.MessageDigest;

public class SHA256 {

    private final static String ALG_NAME = "SHA-256";

    public static String encrypt(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance(ALG_NAME);
            md.update(plainText.getBytes());
            return bytesToHex(md.digest());
        } catch (Exception e) {
            throw new DomainException(ExceptionInformation.ENCRYPT_EXCEPTION);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
