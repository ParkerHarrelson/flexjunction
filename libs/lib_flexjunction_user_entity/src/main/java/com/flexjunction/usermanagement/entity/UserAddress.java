package com.flexjunction.usermanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@Entity
@Table(name = "UserAddress")
public class UserAddress extends ExpirableEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserAddressID", unique = true, nullable = false)
    private Integer userAddressId;

    @Column(name = "StreetAddress", nullable = false)
    private String streetAddress;

    @Column(name = "City", nullable = false)
    private String city;

    @Column(name = "State")
    private String state;

    @Column(name = "Zip", nullable = false)
    private String zipCode;

    @Column(name = "Country", nullable = false)
    private String country;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "UserID")
    @JsonBackReference
    private User user;
}
