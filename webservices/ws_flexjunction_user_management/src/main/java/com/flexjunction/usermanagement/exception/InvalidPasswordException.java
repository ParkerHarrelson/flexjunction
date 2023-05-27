package com.flexjunction.usermanagement.exception;

public class InvalidPasswordException extends RuntimeException{

    private final String username;

    public InvalidPasswordException(String message, String username) {
        super(message);
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }
}
