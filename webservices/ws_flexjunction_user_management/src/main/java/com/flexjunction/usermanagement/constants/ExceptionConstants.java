package com.flexjunction.usermanagement.constants;

public class ExceptionConstants {

    ExceptionConstants() {
    }

    public static final String NON_UNIQUE_USERNAME_EXCEPTION = "Provided username is already taken, please try another.";
    public static final String INVALID_USERNAME_EXCEPTION = "Provided username was invalid because it was either left empty or did not meet the username requirements";
    public static final String INVALID_PASSWORD_EXCEPTION = "Password was invalid because it was either left empty or did not meet the password requirements";
    public static final String PASSWORDS_DONT_MATCH_EXCEPTION = "Password did not match verify password";
    public static final String MISSING_NAME_EXCEPTION = "Either the first or last name field was left empty.";
    public static final String MISSING_SECURITY_QUESTION_EXCEPTION = "No security question and answer was provided. We recommend you have at least 2.";

}
