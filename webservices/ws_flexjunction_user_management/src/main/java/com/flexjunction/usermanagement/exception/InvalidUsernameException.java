package com.flexjunction.usermanagement.exception;

public class InvalidUsernameException extends RuntimeException {

    private final String username;
    public InvalidUsernameException(String message, String username) {
        super(message);
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }
}
