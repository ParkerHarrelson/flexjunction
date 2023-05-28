package com.flexjunction.usermanagement.exception;

public class MissingSecurityQuestionException extends RuntimeException {

    private final String username;

    public MissingSecurityQuestionException(String message, String username) {
        super(message);
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }
}
