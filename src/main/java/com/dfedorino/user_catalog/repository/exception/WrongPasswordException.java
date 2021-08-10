package com.dfedorino.user_catalog.repository.exception;

public class WrongPasswordException extends UserException {
    public WrongPasswordException(String message) {
        super(message);
    }
}
