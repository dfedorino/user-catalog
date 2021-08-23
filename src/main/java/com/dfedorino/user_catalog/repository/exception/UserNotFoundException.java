package com.dfedorino.user_catalog.repository.exception;

public class UserNotFoundException extends UserException {
    public UserNotFoundException() {
        super("User not found");
    }
}
