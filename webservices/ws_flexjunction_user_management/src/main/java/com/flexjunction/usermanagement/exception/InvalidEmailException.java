package com.flexjunction.usermanagement.exception;

public class InvalidEmailException extends RuntimeException {

    private final String username;

    public InvalidEmailException(String message, String username) {
        super(message);
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }
}
