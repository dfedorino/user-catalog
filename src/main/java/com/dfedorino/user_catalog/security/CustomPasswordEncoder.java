package com.dfedorino.user_catalog.security;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;

@Component
public class CustomPasswordEncoder {
    public String generateSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return new String(salt, StandardCharsets.UTF_8);
    }

    @SneakyThrows
    public String generateEncryptedSaltedBytes(String salt, String givenPassword) {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
        messageDigest.update(salt.getBytes(StandardCharsets.UTF_8));
        return new String(messageDigest.digest(givenPassword.getBytes(StandardCharsets.UTF_8)));
    }
}
