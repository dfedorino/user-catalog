package com.dfedorino.user_catalog.presentation.model;

import java.util.Objects;

public class UserBuilder {
    private String login;
    private String password;
    private String email;

    public UserBuilder login(String login) {
        this.login = Objects.requireNonNull(login);
        return this;
    }

    public UserBuilder password(String password) {
        this.password = Objects.requireNonNull(password);
        return this;
    }

    public UserBuilder email(String email) {
        this.email = Objects.requireNonNull(email);
        return this;
    }

    public User build() {
        User user = new User();
        user.setLogin(this.login);
        user.setPassword(this.password);
        user.setEmail(this.email);
        return user;
    }
}
