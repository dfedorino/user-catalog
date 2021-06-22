package com.dfedorino.user_catalog.security;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;

@Component
public class CustomPasswordEncoder {

    @SneakyThrows
    public byte[] generateEncryptedSaltedBytes(String givenPassword) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
        messageDigest.update(salt);
        return messageDigest.digest(givenPassword.getBytes(StandardCharsets.UTF_8));
    }
}
