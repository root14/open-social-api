package com.root14.opensocialapi.repository;

import com.root14.opensocialapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> getUserByUsername(String userName);

    Optional<User> getUserByEmail(String email);

    Optional<User> getUserById(String userId);
}
