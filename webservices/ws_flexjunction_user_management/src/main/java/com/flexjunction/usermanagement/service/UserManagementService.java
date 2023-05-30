package com.flexjunction.usermanagement.service;


import com.flexjunction.usermanagement.dto.PasswordResetRequestDTO;
import com.flexjunction.usermanagement.dto.ResetRequestDTO;
import com.flexjunction.usermanagement.entity.User;
import com.flexjunction.usermanagement.entity.UserPasswordReset;
import com.flexjunction.usermanagement.exception.InvalidPasswordException;
import com.flexjunction.usermanagement.exception.ResetTokenException;
import com.flexjunction.usermanagement.exception.UserNotFoundException;
import com.flexjunction.usermanagement.repository.UserPasswordResetRepository;
import com.flexjunction.usermanagement.repository.UserRepository;
import com.flexjunction.usermanagement.util.EmailUtilService;
import com.flexjunction.usermanagement.util.PasswordUtilService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.UUID;

import static com.flexjunction.usermanagement.constants.ApplicationConstants.*;
import static com.flexjunction.usermanagement.constants.ExceptionConstants.*;

@Service
@AllArgsConstructor
public class UserManagementService {

    private final UserRepository userRepository;
    private final UserPasswordResetRepository userPasswordResetRepository;
    private final PasswordUtilService passwordUtilService;
    private final EmailUtilService emailUtilService;

    @Transactional
    public void changePassword(String username, String oldPassword, String newPassword) {

        User user = userRepository.findByUsernameAndExpirationTimestampIsNull(username)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_EXCEPTION, username)));

        if (!passwordUtilService.passwordsHashMatch(oldPassword, user.getPasswordHash())) {
            throw new InvalidPasswordException(OLD_PASS_DOES_NOT_MATCH_EXCEPTION, username);
        }
        if (passwordUtilService.isNotValidPassword(newPassword)) {
            throw new InvalidPasswordException(INVALID_PASSWORD_EXCEPTION, username);
        }
        if (passwordUtilService.passwordsMatch(oldPassword, newPassword)) {
            throw new InvalidPasswordException(DUPLICATE_PASS_EXCEPTION, username);
        }

        user.setPasswordHash(passwordUtilService.hashPassword(newPassword));
        userRepository.saveAndFlush(user);
    }

    @Transactional
    public String forgotPassword(ResetRequestDTO resetRequest) {
        User user = userRepository.findByEmailAndExpirationTimestampIsNull(resetRequest.getEmail())
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_EXCEPTION, resetRequest.getEmail())));
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);

        Optional<UserPasswordReset> activePassReset = userPasswordResetRepository.findIfUserHasActiveResetToken(user.getUserId(), now);
        activePassReset.ifPresent(userPasswordResetRepository::delete);

        UserPasswordReset userPasswordReset = new UserPasswordReset();
        userPasswordReset.setUser(user);
        userPasswordReset.setEffectiveTimestamp(now);
        userPasswordReset.setExpirationTimestamp(now.plusHours(1L));
        userPasswordReset.setResetToken(UUID.randomUUID().toString());
        userPasswordResetRepository.saveAndFlush(userPasswordReset);

        emailUtilService.sendEmail(user.getEmail(), userPasswordReset.getResetToken(), SUBJECT_PASSWORD_RESET, RESET_PASSWORD, PASSWORD_RESET_ENDPOINT);
        return PASSWORD_RESET_EMAIL_SENT;
    }

    @Transactional
    public void resetPassword(PasswordResetRequestDTO resetRequest) {
        OffsetDateTime now = OffsetDateTime.now(ZoneOffset.UTC);
        UserPasswordReset userPasswordReset = userPasswordResetRepository.findByResetToken(resetRequest.getToken())
                .orElseThrow(() -> new ResetTokenException(FIND_RESET_TOKEN_EXCEPTION));

        if (userPasswordReset.getExpirationTimestamp().isBefore(now)) {
            throw new ResetTokenException(EXPIRED_RESET_TOKEN_EXCEPTION);
        } else if (userPasswordReset.getConfirmedTimestamp() != null) {
            throw new ResetTokenException(RESET_TOKEN_ALREADY_USED_EXCEPTION);
        } else if (passwordUtilService.isNotValidPassword(resetRequest.getPassword())) {
            throw new InvalidPasswordException(INVALID_PASSWORD_EXCEPTION, userPasswordReset.getUser().getUsername());
        }

        userPasswordReset.setConfirmedTimestamp(now);
        userPasswordReset.getUser().setPasswordHash(passwordUtilService.hashPassword(resetRequest.getPassword()));
        userPasswordResetRepository.saveAndFlush(userPasswordReset);
    }

    @Transactional
    public void cancelPasswordReset(String token) {
        UserPasswordReset userPasswordReset = userPasswordResetRepository.findByResetToken(token)
                .orElseThrow(() -> new ResetTokenException(FIND_RESET_TOKEN_EXCEPTION));

        userPasswordResetRepository.delete(userPasswordReset);
    }

}
