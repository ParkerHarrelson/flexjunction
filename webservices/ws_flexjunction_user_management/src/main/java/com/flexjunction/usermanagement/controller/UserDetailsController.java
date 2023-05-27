package com.flexjunction.usermanagement.controller;

import com.flexjunction.usermanagement.service.UserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.flexjunction.usermanagement.constants.ApplicationConstants.URL_USER_DETAILS_BASE;

@RestController
@RequestMapping(URL_USER_DETAILS_BASE)
@AllArgsConstructor
public class UserDetailsController {

    private final UserDetailsService userDetailsService;
}
