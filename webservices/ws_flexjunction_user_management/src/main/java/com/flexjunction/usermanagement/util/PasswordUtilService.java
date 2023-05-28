package com.flexjunction.usermanagement.util;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
@AllArgsConstructor
public class PasswordUtilService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");


    public boolean passwordsMatch(String password, String verifyPassword) {
        return Objects.equals(password, verifyPassword);
    }

    public boolean isValidPassword(String password) {
        if (isBlank(password)) {
            return false;
        }

        Matcher matcher = PASSWORD_PATTERN.matcher(password);
        return matcher.matches();
    }

    public String hashPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }
}
