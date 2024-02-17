package com.example.parking.util.cipher;

import java.security.MessageDigest;

public class SHA256 {

    private final static String ALG_NAME = "SHA-256";

    public static String encrypt(String plainText) {
        try {
            MessageDigest md = MessageDigest.getInstance(ALG_NAME);
            md.update(plainText.getBytes());
            return bytesToHex(md.digest());
        } catch (Exception e) {
            throw new EncryptionException("암호화에 실패했습니다.");
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
