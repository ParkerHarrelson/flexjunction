package com.flexjunction.usermanagement.service;

import com.flexjunction.usermanagement.dto.UserAddressDTO;
import com.flexjunction.usermanagement.dto.UserRegistrationDTO;
import com.flexjunction.usermanagement.dto.UserRegistrationStatusDTO;
import com.flexjunction.usermanagement.entity.User;
import com.flexjunction.usermanagement.entity.UserAccountConfirmation;
import com.flexjunction.usermanagement.entity.UserAddress;
import com.flexjunction.usermanagement.entity.UserSecurityQuestions;
import com.flexjunction.usermanagement.exception.*;
import com.flexjunction.usermanagement.repository.UserAccountConfirmationRepository;
import com.flexjunction.usermanagement.repository.UserRepository;
import com.flexjunction.usermanagement.util.EmailUtilService;
import com.flexjunction.usermanagement.util.PasswordUtilService;
import com.flexjunction.usermanagement.util.UsernameUtilService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.*;
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
    private final EmailUtilService emailUtilService;
    private final UserRepository userRepository;
    private final UserAccountConfirmationRepository userAccountConfirmationRepository;

    @Transactional
    public UserRegistrationStatusDTO registerNewUser(UserRegistrationDTO userRegistrationInfo) {
        String username = userRegistrationInfo.getUsername();
        validateUsername(username);
        validateEmail(userRegistrationInfo.getEmailAddress(), username);
        validateAddress(userRegistrationInfo.getUserAddress(), username);

        String fullName = formFullName(userRegistrationInfo.getFirstName(), userRegistrationInfo.getMiddleName(), userRegistrationInfo.getLastName(), username);
        String passHash = checkPasswordAndGetHash(userRegistrationInfo.getPassword(), userRegistrationInfo.getConfirmPassword(), username);

        User user = buildUser(username, fullName, passHash, userRegistrationInfo.getEmailAddress());
        user.setUserAddress(buildAddress(userRegistrationInfo.getUserAddress(), user));
        user.setSecurityQuestions(buildSecurityQuestions(userRegistrationInfo.getUserSecurityQuestions(), user));

        User savedUser = userRepository.saveAndFlush(user);
        generateConfirmationTokenAndSendEmail(savedUser);

        return UserRegistrationStatusDTO.builder()
                .username(username)
                .status(SUCCESS.getStatus())
                .message(SUCCESSFUL_REGISTRATION)
                .build();
    }

    @Transactional
    public String confirmAccount(String token) {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        Optional<UserAccountConfirmation> confirmationToken = userAccountConfirmationRepository.findByConfirmationToken(token);

        if (confirmationToken.isPresent()) {
            UserAccountConfirmation accountToken = confirmationToken.get();
            if (accountToken.getConfirmedTimestamp() != null) {
                return "This confirmation link has already been used!";
            } else if (accountToken.getExpirationTimestamp().isBefore(now)) {
                return "This confirmation link has expired. Will need to generate a new one.";
            }else {
                accountToken.setConfirmedTimestamp(now);
                accountToken.getUser().setIsAccountConfirmed(true);
                userAccountConfirmationRepository.saveAndFlush(accountToken);
                userRepository.saveAndFlush(accountToken.getUser());
                return "Account Confirmed!";
            }
        } else {
            throw new ConfirmAccountException(String.format(BROKEN_LINK_EXCEPTION, token));
        }
    }

    private void generateConfirmationTokenAndSendEmail(User user) {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);

        UserAccountConfirmation confirmationToken = new UserAccountConfirmation();
        confirmationToken.setConfirmationToken(UUID.randomUUID().toString());
        confirmationToken.setEffectiveTimestamp(now);
        confirmationToken.setExpirationTimestamp(now.plusDays(1L));
        confirmationToken.setUser(user);

        userAccountConfirmationRepository.saveAndFlush(confirmationToken);
        emailUtilService.sendConfirmationEmail(user.getEmail(), confirmationToken.getConfirmationToken());
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

    private void validateEmail(String email, String username) {
        if (!emailUtilService.isValidEmail(email)) {
            throw new InvalidEmailException(INVALID_EMAIL_EXCEPTION, username);
        } else if (!emailUtilService.isAvailableEmail(email)) {
            throw new InvalidEmailException(String.format(NON_UNIQUE_EMAIL_EXCEPTION, email), username);
        }
    }

    private void validateAddress(UserAddressDTO address, String username) {
        if (isBlank(address.getStreetAddress()) || isBlank(address.getCity())
            || isBlank(address.getZipCode()) || isBlank(address.getCountry())) {
            throw new InvalidAddressException(INVALID_ADDRESS_EXCEPTION, username);
        }
    }

    private String formFullName(String first, String middle, String last, String username) {
        if (isBlank(first) || isBlank(last)) {
            throw new MissingNameException(MISSING_NAME_EXCEPTION, username);
        }
        StringJoiner joiner = new StringJoiner(" ");
        joiner.add(first);

        if (!isBlank(middle)) {
            joiner.add(middle);
        }

        joiner.add(last);

        return joiner.toString();
    }

    private User buildUser(String username, String fullName, String passHash, String email) {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);

        User user = new User();
        user.setUsername(username);
        user.setFullName(fullName);
        user.setPasswordHash(passHash);
        user.setEmail(email);
        user.setEffectiveTimestamp(now);
        user.setIsAccountConfirmed(false);

        return user;
    }

    private Set<UserAddress> buildAddress(UserAddressDTO userAddress, User user) {
        UserAddress address = new UserAddress();
        address.setUser(user);
        address.setStreetAddress(userAddress.getStreetAddress());
        address.setCity(userAddress.getCity());
        address.setState(userAddress.getStateAbbreviation());
        address.setCountry(userAddress.getCountry());
        address.setZipCode(userAddress.getZipCode());
        address.setEffectiveTimestamp(user.getEffectiveTimestamp());

        return Set.of(address);
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
                    userSecurityQuestions.setEffectiveTimestamp(user.getEffectiveTimestamp());
                    return userSecurityQuestions;
                }).collect(Collectors.toSet());
    }
}
