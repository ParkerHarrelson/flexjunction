package com.flexjunction.usermanagement.constants;

import lombok.Getter;

public enum RegistrationStatus {

    FAILED("Failed"),
    SUCCESS("Success");

    @Getter
    private final String status;

    RegistrationStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
