package com.flexjunction.usermanagement.controller;

import com.flexjunction.usermanagement.dto.UserRegistrationDTO;
import com.flexjunction.usermanagement.dto.UserRegistrationStatusDTO;
import com.flexjunction.usermanagement.service.RateLimitingService;
import com.flexjunction.usermanagement.service.UserRegistrationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.flexjunction.usermanagement.constants.ApplicationConstants.URL_USER_REGISTER;
import static com.flexjunction.usermanagement.constants.ApplicationConstants.URL_USER_REGISTRATION_BASE;

@RestController
@RequestMapping(URL_USER_REGISTRATION_BASE)
@AllArgsConstructor
public class UserRegistrationController {

    private final UserRegistrationService userRegistrationService;
    private final RateLimitingService rateLimitingService;

    @PostMapping(path = URL_USER_REGISTER)
    public ResponseEntity<UserRegistrationStatusDTO> userRegistration(HttpServletRequest request,
                                                                      @RequestBody UserRegistrationDTO userRegistrationInfo) {
        if (rateLimitingService.isAllowed(request)) {
            return new ResponseEntity<>(userRegistrationService.registerNewUser(userRegistrationInfo), HttpStatus.CREATED);
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
    }
}
