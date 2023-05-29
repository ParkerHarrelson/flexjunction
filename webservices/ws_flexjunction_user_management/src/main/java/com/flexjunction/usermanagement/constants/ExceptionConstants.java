package com.flexjunction.usermanagement.constants;

public class ExceptionConstants {

    ExceptionConstants() {
    }

    public static final String NON_UNIQUE_USERNAME_EXCEPTION = "Provided username is already taken, please try another.";
    public static final String NON_UNIQUE_EMAIL_EXCEPTION = "Provided email address %s is already taken, please try another or try logging in.";
    public static final String INVALID_USERNAME_EXCEPTION = "Provided username was invalid because it was either left empty or did not meet the username requirements";
    public static final String INVALID_PASSWORD_EXCEPTION = "Password was invalid because it was either left empty or did not meet the password requirements";
    public static final String PASSWORDS_DONT_MATCH_EXCEPTION = "Password did not match verify password";
    public static final String MISSING_NAME_EXCEPTION = "Either the first or last name field was left empty.";
    public static final String MISSING_SECURITY_QUESTION_EXCEPTION = "No security question and answer was provided. We recommend you have at least 2.";
    public static final String INVALID_EMAIL_EXCEPTION = "Provided email was invalid because it either was not a valid address or was left blank";
    public static final String INVALID_ADDRESS_EXCEPTION = "One or more fields in the address form were left blank.";
    public static final String EMAIL_EXCEPTION = "Failed to send email to %s";
    public static final String BROKEN_LINK_EXCEPTION = "The link is invalid or broken! Token = %s";
    public static final String USER_NOT_FOUND_EXCEPTION = "User %s not found.";
    public static final String OLD_PASS_DOES_NOT_MATCH_EXCEPTION = "Old password does not match!";
    public static final String DUPLICATE_PASS_EXCEPTION = "New password is the same as old password. Please choose a new password.";
}
