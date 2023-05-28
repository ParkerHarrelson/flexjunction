package com.flexjunction.usermanagement.repository;

import com.flexjunction.usermanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsernameAndExpirationTimestampIsNull(String username);
}
