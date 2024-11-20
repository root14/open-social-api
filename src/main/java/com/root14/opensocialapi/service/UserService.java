package com.root14.opensocialapi.service;

import com.root14.opensocialapi.dao.LoginResponse;
import com.root14.opensocialapi.dao.UserLoginDao;
import com.root14.opensocialapi.dao.UserRegisterDao;
import com.root14.opensocialapi.dao.ForgotPasswordDao;
import com.root14.opensocialapi.entity.User;
import com.root14.opensocialapi.exception.ErrorType;
import com.root14.opensocialapi.exception.UserException;
import com.root14.opensocialapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User getSocialUserByUserName(String userName) throws Exception {
        return userRepository.getUserByUsername(userName).orElseThrow(() -> new Exception("User not found"));
    }

    public User getSocialUserByEmail(String email) throws Exception {
        return userRepository.getUserByEmail(email).orElseThrow(() -> new Exception("User not found"));
    }

    public User getSocialUserByUserId(Long userId) throws Exception {
        return userRepository.getUserById(userId).orElseThrow(() -> new Exception("User not found"));
    }

    public ResponseEntity<LoginResponse> authenticate(UserLoginDao userLoginDao) throws UserException {
        if (userRepository.existsUserByEmailAndUsername(userLoginDao.getEmail(), userLoginDao.getUserName())) {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDao.getUserName(), userLoginDao.getPassword()));
            User foundedUser = userRepository.getUserByUsername(userLoginDao.getUserName()).orElseThrow();

            String jwtToken = jwtService.generateToken(foundedUser);

            LoginResponse loginResponse = LoginResponse.builder().token(jwtToken).expiresIn(jwtService.getExpirationTime()).build();

            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        } else {
            throw UserException.builder().errorType(ErrorType.USER_NOT_FOUND).httpStatus(HttpStatus.BAD_REQUEST).errorMessage("User not found").build();
        }

    }

    public ResponseEntity<String> saveSocialUser(UserRegisterDao userRegisterDao) throws UserException {
        if (!userRepository.existsUserByEmailAndUsername(userRegisterDao.getEmail(), userRegisterDao.getUserName())) {
            Optional<User> optionalUser = userRepository.getUserByUsername(userRegisterDao.getUserName());

            if (optionalUser.isPresent()) {
                throw UserException.builder().errorType(ErrorType.USER_ALREADY_EXISTS).httpStatus(HttpStatus.BAD_REQUEST).errorMessage("User already exists").build();
            }

            User user = User.builder().createdAt(LocalDateTime.now()).updatedAt(LocalDateTime.now()).email(userRegisterDao.getEmail()).password(passwordEncoder.encode(userRegisterDao.getPassword())).username(userRegisterDao.getUserName()).build();

            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("User saved.");
        } else {
            throw UserException.builder().errorType(ErrorType.DAO_BAD_FORMAT).httpStatus(HttpStatus.BAD_REQUEST).errorMessage("Credential already exists").build();
        }
    }

    public ResponseEntity<String> updateSocialUser(ForgotPasswordDao forgotPasswordDao) throws UserException {
        if (userRepository.existsUserByEmailAndUsername(forgotPasswordDao.getEmail(), forgotPasswordDao.getUserName())) {
            Optional<User> optionalUser = userRepository.getUserByEmail(forgotPasswordDao.getEmail());

            if (optionalUser.isPresent()) {
                User user = optionalUser.get();

                user.setUpdatedAt(LocalDateTime.now());
                user.setPassword(forgotPasswordDao.getPassword());

                userRepository.save(user);
                return ResponseEntity.ok().body("User updated.");
            } else {
                throw UserException.builder().httpStatus(HttpStatus.BAD_REQUEST).errorType(ErrorType.USER_CANNOT_UPDATE).errorMessage("User cannot update.").build();
            }
        } else {
            throw UserException.builder().errorType(ErrorType.DAO_BAD_FORMAT).httpStatus(HttpStatus.BAD_REQUEST).errorMessage("User not found.").build();
        }
    }
}
