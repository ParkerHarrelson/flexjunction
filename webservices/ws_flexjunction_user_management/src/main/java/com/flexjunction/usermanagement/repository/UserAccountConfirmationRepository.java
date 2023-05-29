package com.flexjunction.usermanagement.repository;

import com.flexjunction.usermanagement.entity.UserAccountConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserAccountConfirmationRepository extends JpaRepository<UserAccountConfirmation, Integer> {

    @Query(
            "SELECT UserAccountConfirmation " +
                    "FROM UserAccountConfirmation AS confirmation " +
                    "LEFT JOIN User AS user_info ON user_info.userId = confirmation.user.userId " +
                    "WHERE user_info.email = :email " +
                    "AND confirmation.confirmedTimestamp IS NULL " +
                    "AND user_info.isAccountConfirmed = FALSE"
    )
    Optional<UserAccountConfirmation> findExisingConfirmation(String email);

    Optional<UserAccountConfirmation> findByConfirmationToken(String token);
}
