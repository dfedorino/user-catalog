package com.dfedorino.user_catalog.security;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomPasswordEncoderTest {
    CustomPasswordEncoder passwordEncoder = new CustomPasswordEncoder();

    @Test
    public void testGetSalt_whenNewLogin_thenNewSalt() {
        assertThat(passwordEncoder.generateSalt("login")).isNotNull();
    }

    @Test
    void generateEncryptedSaltedPassword_whenGivenAPassword_thenOutputIsNotEqualToGiven() {
        String givenLogin = "login";
        String givenPassword = "password";
        String hashedPassword = new String(passwordEncoder.generateEncryptedSaltedBytes(givenLogin, givenPassword));
        assertThat(hashedPassword).isNotEqualTo(givenPassword);
    }

    @Test
    void generateEncryptedSaltedPassword_whenHashingAPassword_thenAlwaysReturnsSameResult() {
        String givenLogin = "login";
        String givenPassword = "password";
        String hashedPassword = new String(passwordEncoder.generateEncryptedSaltedBytes(givenLogin, givenPassword));
        String sameHashedPassword = new String(passwordEncoder.generateEncryptedSaltedBytes(givenLogin, givenPassword));
        assertThat(hashedPassword).isEqualTo(sameHashedPassword);
    }
}