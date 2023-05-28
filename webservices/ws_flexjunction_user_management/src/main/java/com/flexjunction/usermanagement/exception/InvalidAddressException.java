package com.flexjunction.usermanagement.exception;

public class InvalidAddressException extends RuntimeException {

    private final String username;

    public InvalidAddressException(String message, String username) {
        super(message);
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }
}
