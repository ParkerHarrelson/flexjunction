package com.flexjunction.usermanagement.util;

import com.flexjunction.usermanagement.entity.User;
import com.flexjunction.usermanagement.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
@AllArgsConstructor
public class UsernameUtilService {

    private final UserRepository userRepository;
    private final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]{3,20}$");

    public boolean isValidUsername(String username) {
        if (isBlank(username)) {
            return false;
        }
        Matcher matcher = USERNAME_PATTERN.matcher(username);
        return matcher.matches();
    }

    public boolean isUniqueUsername(String username) {
        Optional<User> existingUser = userRepository.findByUsernameAndExpirationTimestampIsNull(username);
        return existingUser.isEmpty();
    }
}
