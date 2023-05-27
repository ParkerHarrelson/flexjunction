package com.flexjunction.usermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAddressDTO {
    private String streetAddress;
    private String city;
    private String stateAbbreviation;
    private String zipCode;
    private String country;
}
