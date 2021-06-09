package com.dfedorino.user_catalog.repository.exception;

public class UserAlreadyExistsException extends RuntimeException{
    public UserAlreadyExistsException() {
        super("User with same login or email already exists");
    }
}
