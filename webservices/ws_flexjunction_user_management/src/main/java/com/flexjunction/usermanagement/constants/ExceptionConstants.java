package com.flexjunction.usermanagement.constants;

public class ExceptionConstants {

    ExceptionConstants() {
    }

    public static final String NON_UNIQUE_USERNAME_EXCEPTION = "Provided username is already taken, please try another.";
    public static final String INVALID_USERNAME_EXCEPTION = "Provided username was invalid because it was either left enmpty or did not meet the username requirements";
    public static final String INVALID_PASSWORD_EXCEPTION = "Password was invalid because it was either left empty or did not meet the password requirements";
    public static final String PASSWORDS_DONT_MATCH_EXCEPTION = "Password did not match verify password";

}
