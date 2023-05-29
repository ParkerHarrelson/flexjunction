package com.flexjunction.usermanagement.controller;

import com.flexjunction.usermanagement.service.UserManagementService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.flexjunction.usermanagement.constants.ApplicationConstants.URL_USER_MANAGEMENT_BASE;

@RestController
@RequestMapping(URL_USER_MANAGEMENT_BASE)
@AllArgsConstructor
public class UserManagementController {

    private final UserManagementService userManagementService;

    // endpoint for changing password

    // endpoint for updating general profile info

    // endpoint for deleting account (actually expires account)

    // endpoint for modifying bio
}
