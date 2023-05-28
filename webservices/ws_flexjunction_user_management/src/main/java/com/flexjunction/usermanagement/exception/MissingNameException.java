package com.flexjunction.usermanagement.exception;

public class MissingNameException extends RuntimeException {

    private final String username;

    public MissingNameException(String message, String username) {
        super(message);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
