package com.flexjunction.usermanagement.controller;

import com.flexjunction.usermanagement.service.UserManagementService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.flexjunction.usermanagement.constants.ApplicationConstants.URL_CHANGE_PASSWORD;
import static com.flexjunction.usermanagement.constants.ApplicationConstants.URL_USER_MANAGEMENT_BASE;

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

    // forgot password endpoint
}
