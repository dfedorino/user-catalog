package com.dfedorino.user_catalog.security;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomPasswordEncoderTest {
    CustomPasswordEncoder passwordEncoder = new CustomPasswordEncoder();

    @Test
    public void testGetSalt_whenNewLogin_thenNewSalt() {
        assertThat(passwordEncoder.generateSalt()).isNotNull();
    }

    @Test
    void generateEncryptedSaltedPassword_whenGivenAPassword_thenOutputIsNotEqualToGiven() {
        String salt = new String(passwordEncoder.generateSalt());
        String givenPassword = "password";
        String hashedPassword = new String(passwordEncoder.generateEncryptedSaltedBytes(salt, givenPassword));
        assertThat(hashedPassword).isNotEqualTo(givenPassword);
    }

    @Test
    void generateEncryptedSaltedPassword_whenHashingAPassword_thenAlwaysReturnsSameResult() {
        String salt = new String(passwordEncoder.generateSalt());
        String givenPassword = "password";
        String hashedPassword = new String(passwordEncoder.generateEncryptedSaltedBytes(salt, givenPassword));
        String sameHashedPassword = new String(passwordEncoder.generateEncryptedSaltedBytes(salt, givenPassword));
        assertThat(hashedPassword).isEqualTo(sameHashedPassword);
    }
}