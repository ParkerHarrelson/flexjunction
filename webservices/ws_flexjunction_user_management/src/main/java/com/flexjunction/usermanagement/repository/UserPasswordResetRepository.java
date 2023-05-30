package com.flexjunction.usermanagement.repository;

import com.flexjunction.usermanagement.entity.UserPasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;

@Repository
public interface UserPasswordResetRepository extends JpaRepository<UserPasswordReset, Integer> {
    Optional<UserPasswordReset> findByResetToken(String resetToken);

    @Query(
            "SELECT UserPasswordReset " +
                    "FROM UserPasswordReset passReset " +
                    "LEFT JOIN User user ON passReset.user.userId = user.userId " +
                    "WHERE user.userId = :userID " +
                    "AND passReset.confirmedTimestamp IS NULL " +
                    "AND passReset.expirationTimestamp > :now"
    )
    Optional<UserPasswordReset> findIfUserHasActiveResetToken(Integer userID, OffsetDateTime now);
}
