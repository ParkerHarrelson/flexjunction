package com.flexjunction.usermanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "UserAccountConfirmation")
public class UserAccountConfirmation extends ExpirableEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserConfirmationID", unique = true, nullable = false)
    private Integer userConfirmationId;

    @Column(name = "ConfirmationToken")
    private String confirmationToken;

    @Column(name = "ConfirmedTimestamp", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime confirmedTimestamp;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "UserID")
    @JsonBackReference
    private User user;
}
