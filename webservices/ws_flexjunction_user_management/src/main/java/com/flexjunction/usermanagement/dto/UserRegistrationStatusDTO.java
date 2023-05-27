package com.flexjunction.usermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegistrationStatusDTO {
    private String username;
    private String status;
    private String message;
}
