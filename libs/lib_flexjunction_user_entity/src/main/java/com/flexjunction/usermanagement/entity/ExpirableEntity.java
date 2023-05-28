package com.flexjunction.usermanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;

import java.time.OffsetDateTime;

@MappedSuperclass
@Data
public class ExpirableEntity {
    @Column(name = "EffectiveTimestamp", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime effectiveTimestamp;

    @Column(name = "ExpirationTimestamp", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime expirationTimestamp;
}
