package com.flexjunction.usermanagement.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@Data
@Entity
@Table(name = "UserSecurityQuestions")
public class UserSecurityQuestions extends ExpirableEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserQuestionID", unique = true, nullable = false)
    private Integer userQuestionId;

    @Column(name = "SecurityQuestion", nullable = false)
    private String question;

    @Column(name = "SecurityAnswer", nullable = false)
    private String answer;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "UserID")
    @JsonBackReference
    private User user;
}
