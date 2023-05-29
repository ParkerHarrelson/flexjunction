package com.flexjunction.usermanagement.util;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
@AllArgsConstructor
public class PasswordUtilService {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private final Pattern PASSWORD_PATTERN = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$");


    public boolean passwordsMatch(String password, String verifyPassword) {
        return Objects.equals(password, verifyPassword);
    }

    public boolean isNotValidPassword(String password) {
        if (isBlank(password)) {
            return true;
        }

        Matcher matcher = PASSWORD_PATTERN.matcher(password);
        return !matcher.matches();
    }

    public String hashPassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }

    public boolean passwordsHashMatch(String passOne, String passTwo) {
        return isNotBlank(passOne) && bCryptPasswordEncoder.matches(passOne, passTwo);
    }
}
