package com.flexjunction.usermanagement.service;

import com.flexjunction.usermanagement.dto.UserRegistrationDTO;
import com.flexjunction.usermanagement.dto.UserRegistrationStatusDTO;
import com.flexjunction.usermanagement.entity.User;
import com.flexjunction.usermanagement.entity.UserSecurityQuestions;
import com.flexjunction.usermanagement.exception.InvalidPasswordException;
import com.flexjunction.usermanagement.exception.InvalidUsernameException;
import com.flexjunction.usermanagement.exception.MissingNameException;
import com.flexjunction.usermanagement.exception.MissingSecurityQuestionException;
import com.flexjunction.usermanagement.repository.UserRepository;
import com.flexjunction.usermanagement.util.PasswordUtilService;
import com.flexjunction.usermanagement.util.UsernameUtilService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.flexjunction.usermanagement.constants.ExceptionConstants.*;
import static com.flexjunction.usermanagement.constants.ApplicationConstants.SUCCESSFUL_REGISTRATION;
import static com.flexjunction.usermanagement.constants.RegistrationStatus.SUCCESS;
import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
@AllArgsConstructor
public class UserRegistrationService {

    private final PasswordUtilService passwordUtilService;
    private final UsernameUtilService usernameUtilService;
    private final UserRepository userRepository;

    public UserRegistrationStatusDTO registerNewUser(UserRegistrationDTO userRegistrationInfo) {
        String username = userRegistrationInfo.getUsername();
        validateUsername(username);

        String fullName = formFullName(userRegistrationInfo.getFirstName(), userRegistrationInfo.getMiddleName(), userRegistrationInfo.getLastName(), username);
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

    private String formFullName(String first, String middle, String last, String username) {
        if (isBlank(first) || isBlank(last)) {
            throw new MissingNameException(MISSING_NAME_EXCEPTION, username);
        } else {
            if (isBlank(middle)) {
                return first + " " + last;
            } else {
                return first + " " + middle + " " + last;
            }
        }
    }

    private User buildUser() {

    }

    private Set<UserSecurityQuestions> buildSecurityQuestions(Map<String, String> securityQuestions, User user) {
        if (CollectionUtils.isEmpty(securityQuestions)) {
            throw new MissingSecurityQuestionException(MISSING_SECURITY_QUESTION_EXCEPTION, user.getUsername());
        }

        return securityQuestions.entrySet().stream()
                .map(entry -> {
                    UserSecurityQuestions userSecurityQuestions = new UserSecurityQuestions();
                    userSecurityQuestions.setQuestion(entry.getKey());
                    userSecurityQuestions.setAnswer(entry.getValue());
                    userSecurityQuestions.setUser(user);
                    return userSecurityQuestions;
                }).collect(Collectors.toSet());
    }
}
