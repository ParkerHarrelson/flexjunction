package com.flexjunction.usermanagement.service;


import com.flexjunction.usermanagement.dto.PasswordResetRequestDTO;
import com.flexjunction.usermanagement.dto.ResetRequestDTO;
import com.flexjunction.usermanagement.entity.User;
import com.flexjunction.usermanagement.exception.InvalidPasswordException;
import com.flexjunction.usermanagement.exception.UserNotFoundException;
import com.flexjunction.usermanagement.repository.UserRepository;
import com.flexjunction.usermanagement.util.PasswordUtilService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.flexjunction.usermanagement.constants.ExceptionConstants.*;

@Service
@AllArgsConstructor
public class UserManagementService {

    private final UserRepository userRepository;
    private final PasswordUtilService passwordUtilService;

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

    }

    @Transactional
    public String resetPassword(PasswordResetRequestDTO resetRequest) {

    }

    @Transactional
    public void cancelPasswordReset(String token) {

    }

}
