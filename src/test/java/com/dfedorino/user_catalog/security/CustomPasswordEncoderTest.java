package com.dfedorino.user_catalog.security;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CustomPasswordEncoderTest {
    CustomPasswordEncoder passwordEncoder = new CustomPasswordEncoder();

    @Test
    void generateEncryptedSaltedPassword_whenGivenAPassword_thenOutputIsNotEqualToGiven() {
        String givenPassword = "password";
        String hashedPassword = new String(passwordEncoder.generateEncryptedSaltedBytes(givenPassword));
        assertThat(hashedPassword).isNotEqualTo(givenPassword);
    }
}