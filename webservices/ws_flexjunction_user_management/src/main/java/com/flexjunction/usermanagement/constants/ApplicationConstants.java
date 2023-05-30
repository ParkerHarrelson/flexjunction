package com.flexjunction.usermanagement.constants;

public class ApplicationConstants {

    ApplicationConstants(){
    }

    // URLS
    public static final String URL_USER_MANAGEMENT_BASE = "/user-management";
    public static final String URL_CHANGE_PASSWORD = "/{username}/change-password";
    public static final String URL_USER_DETAILS_BASE = "/user-details";
    public static final String URL_USER_REGISTRATION_BASE = "/user-registration";
    public static final String URL_CONFIRM_ACCOUNT = "/confirm-account";
    public static final String URL_USER_REGISTER = "/register";
    public static final String URL_RESEND_CONFIRMATION = "/resend-confirmation";
    public static final String URL_FORGOT_PASSWORD = "/forgot-password";
    public static final String URL_RESET_PASSWORD = "/reset-password";
    public static final String URL_CANCEL_PASSWORD_RESET = "/cancel";

    // MESSAGES
    public static final String SUCCESSFUL_REGISTRATION = "User was successfully registered.";
    public static final String PASSWORD_RESET_EMAIL_SENT = "Password email was successfully sent";
    public static final String CONFIRM_ACCOUNT = "To confirm your account, please click the following link: ";
    public static final String RESET_PASSWORD = "To reset your password, please click the following link and fill in all necessary info: ";
    public static final String SUBJECT_ACCOUNT_CONFIRMATION = "FlexJunction Account Confirmation";
    public static final String SUBJECT_PASSWORD_RESET = "FlexJunction Password Reset";
    public static final String CONFIRMATION_EMAIL_ENDPOINT = "http://localhost:8080/ws_flexjunction_user_management/user-registration/confirm-account?confirmation-token=";
    public static final String PASSWORD_RESET_ENDPOINT = "http://localhost:8080/ws_flexjunction_user_management/user-management/reset-password?token=";
}
