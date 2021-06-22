package com.dfedorino.user_catalog.security;

import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class CustomPasswordEncoder {
    private final Map<String, byte[]> loginToSaltMap = new ConcurrentHashMap<>();

    public byte[] generateSalt(String login) {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }

    @SneakyThrows
    public byte[] generateEncryptedSaltedBytes(String login, String givenPassword) {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
        messageDigest.update(loginToSaltMap.computeIfAbsent(login, this::generateSalt));
        return messageDigest.digest(givenPassword.getBytes(StandardCharsets.UTF_8));
    }
}
