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
public class EmailUtilService {

    private final UserRepository userRepository;
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final Pattern pattern = Pattern.compile(EMAIL_PATTERN);

    public boolean isValidEmail(String email) {
        if (isBlank(email)) {
            return false;
        }

        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isAvailableEmail(String email) {
        Optional<User> existingUser = userRepository.findByEmailAndExpirationTimestampIsNull(email);
        return existingUser.isEmpty();
    }

}
