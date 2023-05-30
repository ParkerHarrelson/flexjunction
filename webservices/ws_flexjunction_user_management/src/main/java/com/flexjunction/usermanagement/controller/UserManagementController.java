package com.flexjunction.usermanagement.controller;

import com.flexjunction.usermanagement.dto.PasswordResetRequestDTO;
import com.flexjunction.usermanagement.dto.ResetRequestDTO;
import com.flexjunction.usermanagement.service.UserManagementService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.flexjunction.usermanagement.constants.ApplicationConstants.*;

@RestController
@RequestMapping(URL_USER_MANAGEMENT_BASE)
@AllArgsConstructor
public class UserManagementController {

    private final UserManagementService userManagementService;

    @PutMapping(path = URL_CHANGE_PASSWORD)
    public ResponseEntity<Void> changePassword(@PathVariable("username") String username,
                                               @RequestParam("oldPassword") String oldPassword,
                                               @RequestParam("newPassword") String newPassword) {
        userManagementService.changePassword(username, oldPassword, newPassword);
        return ResponseEntity.noContent().build();
    }

    // endpoint for updating general profile info

    // endpoint for deleting account (actually expires account)

    // endpoint for modifying bio

    @PostMapping(path = URL_FORGOT_PASSWORD)
    public ResponseEntity<String> forgotPassword(@RequestBody ResetRequestDTO resetRequest) {
        return new ResponseEntity<>(userManagementService.forgotPassword(resetRequest), HttpStatus.OK);
    }

    @PostMapping(path = URL_RESET_PASSWORD)
    public ResponseEntity<Void> resetPassword(@RequestBody PasswordResetRequestDTO resetRequest) {
        userManagementService.resetPassword(resetRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = URL_RESET_PASSWORD + URL_CANCEL_PASSWORD_RESET)
    public ResponseEntity<Void> cancelPasswordReset(@RequestParam("reset-token") String token) {
        userManagementService.cancelPasswordReset(token);
        return ResponseEntity.noContent().build();
    }
}
