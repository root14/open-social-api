package com.root14.opensocialapi.service;

import com.root14.opensocialapi.dao.UserRegisterDao;
import com.root14.opensocialapi.dao.UserUpdateDao;
import com.root14.opensocialapi.entity.User;
import com.root14.opensocialapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User getSocialUserByUserName(String userName) throws Exception {
        return userRepository.getUserByUsername(userName).orElseThrow(() -> new Exception("User not found"));
    }

    public User getSocialUserByEmail(String email) throws Exception {
        return userRepository.getUserByEmail(email).orElseThrow(() -> new Exception("User not found"));
    }

    public User getSocialUserByUserId(String userId) throws Exception {
        return userRepository.getUserById(userId).orElseThrow(() -> new Exception("User not found"));
    }

    public ResponseEntity<String> saveSocialUser(UserRegisterDao userRegisterDao) throws Exception {
        Optional<User> optionalUser = userRepository.getUserByEmail(userRegisterDao.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
            return ResponseEntity.ok().body("User saved.");
        } else {
            throw new Exception("user cannot saved.");
        }
    }

    public ResponseEntity<String> updateSocialUser(UserUpdateDao userUpdateDao) throws Exception {

        Optional<User> optionalUser = userRepository.getUserByEmail(userUpdateDao.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setUpdatedAt(LocalDateTime.now());
            user.setUsername(userUpdateDao.getEmail());
            user.setEmail(userUpdateDao.getEmail());
            user.setPassword(userUpdateDao.getPassword());

            userRepository.save(user);
            return ResponseEntity.ok().body("User updated.");
        } else {
            throw new Exception("user cannot update.");
        }
    }
}
