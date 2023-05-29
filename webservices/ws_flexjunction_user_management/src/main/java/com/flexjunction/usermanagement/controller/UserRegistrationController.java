package com.flexjunction.usermanagement.controller;

import com.flexjunction.usermanagement.dto.ResetRequestDTO;
import com.flexjunction.usermanagement.dto.UserRegistrationDTO;
import com.flexjunction.usermanagement.dto.UserRegistrationStatusDTO;
import com.flexjunction.usermanagement.service.UserRegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.flexjunction.usermanagement.constants.ApplicationConstants.*;

@RestController
@RequestMapping(URL_USER_REGISTRATION_BASE)
@AllArgsConstructor
public class UserRegistrationController {

    private final UserRegistrationService userRegistrationService;

    @PostMapping(path = URL_USER_REGISTER)
    public ResponseEntity<UserRegistrationStatusDTO> userRegistration(@RequestBody UserRegistrationDTO userRegistrationInfo) {
        return new ResponseEntity<>(userRegistrationService.registerNewUser(userRegistrationInfo), HttpStatus.CREATED);
    }

    @GetMapping(path = URL_CONFIRM_ACCOUNT)
    public ResponseEntity<String> confirmAccount(@RequestParam("confirmation-token") String token) {
        return new ResponseEntity<>(userRegistrationService.confirmAccount(token), HttpStatus.OK);
    }

    @PostMapping(path = URL_RESEND_CONFIRMATION)
    public ResponseEntity<String> resendConfirmation(@RequestBody ResetRequestDTO resendConfirmationRequest) {
        return new ResponseEntity<>(userRegistrationService.resendConfirmationEmail(resendConfirmationRequest), HttpStatus.OK);
    }

}
