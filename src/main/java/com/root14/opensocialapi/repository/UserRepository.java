package com.root14.opensocialapi.repository;

import com.root14.opensocialapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Locale;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByUsername(String userName);

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserById(Long userId);

    Boolean existsUserByEmailAndUsername(String email, String username);
}
