package com.dfedorino.user_catalog.repository;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UserTest extends BaseEntityTest {
    @Test
    public void testGetLogin() {
        assertThat(new UserBuilder().build().getLogin()).isNull();
    }

    @Test
    public void testGetPassword() {
        assertThat(new UserBuilder().build().getPassword()).isNull();
    }

    @Test
    public void testGetEmail() {
        assertThat(new UserBuilder().build().getEmail()).isNull();
    }

    @Test
    public void testSetLogin() {
        User user = new UserBuilder().login("login").build();
        assertThat(user.getLogin()).isEqualTo("login");
        assertThat(user.getPassword()).isNull();
        assertThat(user.getEmail()).isNull();
    }

    @Test
    public void testSetPassword() {
        User user = new UserBuilder().password("password").build();
        assertThat(user.getLogin()).isNull();
        assertThat(user.getPassword()).isEqualTo("password");
        assertThat(user.getEmail()).isNull();
    }

    @Test
    public void testSetEmail() {
        User user = new UserBuilder().email("email").build();
        assertThat(user.getLogin()).isNull();
        assertThat(user.getLogin()).isNull();
        assertThat(user.getEmail()).isEqualTo("email");
    }

    @Test
    public void testTestEquals_AllFieldsAreSame_UsersAreEqual() {
        assertThat(
                new UserBuilder()
                        .login("login")
                        .password("password")
                        .email("email")
                        .build())
        .isEqualTo(
                new UserBuilder()
                        .login("login")
                        .password("password")
                        .email("email")
                        .build());
    }

    @Test
    public void testTestEquals_EmptyUsers_UsersAreEqual() {
        User first = new UserBuilder().build();
        User second = new UserBuilder().build();
        assertThat(first).isEqualTo(second);
        assertThat(first.hashCode()).isEqualTo(second.hashCode());
    }

    @Test
    public void testTestEquals_AllFieldsAreSameExceptName_UsersAreNotEqual() {
        User first = new UserBuilder().login("login1").password("pass1").email("email1").build();
        User second = new UserBuilder().login("login2").password("pass2").email("email2").build();
        assertThat(first).isNotEqualTo(second);
        assertThat(first.hashCode()).isNotEqualTo(second.hashCode());
    }
}