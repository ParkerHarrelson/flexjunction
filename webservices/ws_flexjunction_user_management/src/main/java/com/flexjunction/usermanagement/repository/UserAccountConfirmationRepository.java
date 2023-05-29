package com.flexjunction.usermanagement.repository;

import com.flexjunction.usermanagement.entity.UserAccountConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountConfirmationRepository extends JpaRepository<UserAccountConfirmation, Integer> {

    Optional<UserAccountConfirmation> findByConfirmationToken(String token);
}
