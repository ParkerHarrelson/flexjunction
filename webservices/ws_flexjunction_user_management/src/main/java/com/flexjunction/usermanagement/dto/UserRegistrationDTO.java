package com.flexjunction.usermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegistrationDTO {
    private String username;
    private String emailAddress;
    private String password;
    private String confirmPassword;
    private String phoneNumber;
    private String fullName;
    private UserAddressDTO userAddress;
    private String dateOfBirth;
    private Map<String, String> userSecurityQuestions;
    private boolean termsConsented = false;
}
