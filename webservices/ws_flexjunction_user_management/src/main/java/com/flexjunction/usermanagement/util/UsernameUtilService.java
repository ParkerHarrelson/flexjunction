package com.flexjunction.usermanagement.util;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
@AllArgsConstructor
public class UsernameUtilService {

    // repository for finding existing usernames

    private final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_-]{3,20}$");

    public boolean isValidUsername(String username) {
        if (isBlank(username)) {
            return false;
        }
        Matcher matcher = USERNAME_PATTERN.matcher(username);
        return matcher.matches();
    }

    public boolean isUniqueUsername(String username) {
        // find if username matches any existing usernames
        return true;
    }
}
