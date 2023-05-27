package com.flexjunction.usermanagement.service;

import com.flexjunction.usermanagement.dto.UserRegistrationDTO;
import com.flexjunction.usermanagement.dto.UserRegistrationStatusDTO;
import com.flexjunction.usermanagement.exception.InvalidPasswordException;
import com.flexjunction.usermanagement.exception.InvalidUsernameException;
import com.flexjunction.usermanagement.util.PasswordUtilService;
import com.flexjunction.usermanagement.util.UsernameUtilService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.flexjunction.usermanagement.constants.ExceptionConstants.*;
import static com.flexjunction.usermanagement.constants.ApplicationConstants.SUCCESSFUL_REGISTRATION;
import static com.flexjunction.usermanagement.constants.RegistrationStatus.SUCCESS;

@Service
@AllArgsConstructor
public class UserRegistrationService {

    private final PasswordUtilService passwordUtilService;
    private final UsernameUtilService usernameUtilService;

    public UserRegistrationStatusDTO registerNewUser(UserRegistrationDTO userRegistrationInfo) {
        String username = userRegistrationInfo.getUsername();
        validateUsername(username);

        String passHash = checkPasswordAndGetHash(userRegistrationInfo.getPassword(), userRegistrationInfo.getConfirmPassword(), username);

        return UserRegistrationStatusDTO.builder()
                .username(username)
                .status(SUCCESS.getStatus())
                .message(SUCCESSFUL_REGISTRATION)
                .build();
    }

    private String checkPasswordAndGetHash(String password, String verifyPassword, String username) {
        if (!passwordUtilService.isValidPassword(password)) {
            throw new InvalidPasswordException(INVALID_PASSWORD_EXCEPTION, username);
        } else if (!passwordUtilService.passwordsMatch(password, verifyPassword)) {
            throw new InvalidPasswordException(PASSWORDS_DONT_MATCH_EXCEPTION, username);
        } else {
            return passwordUtilService.hashPassword(password);
        }
    }

    private void validateUsername(String username) {
        if (!usernameUtilService.isValidUsername(username)) {
            throw new InvalidUsernameException(INVALID_USERNAME_EXCEPTION, username);
        } else if (!usernameUtilService.isUniqueUsername(username)) {
            throw new InvalidUsernameException(NON_UNIQUE_USERNAME_EXCEPTION, username);
        }
    }


}
