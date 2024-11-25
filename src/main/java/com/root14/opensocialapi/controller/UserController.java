package com.root14.opensocialapi.controller;

import com.root14.opensocialapi.dto.LoginResponse;
import com.root14.opensocialapi.dto.UserLoginDto;
import com.root14.opensocialapi.dto.UserRegisterDto;
import com.root14.opensocialapi.dto.ForgotPasswordDto;
import com.root14.opensocialapi.entity.User;
import com.root14.opensocialapi.exception.ErrorType;
import com.root14.opensocialapi.exception.UserException;
import com.root14.opensocialapi.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    //todo add has role admin
    @GetMapping("/getAllUser")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegisterDto userRegisterDto, BindingResult bindingResult) throws UserException {

        if (bindingResult.hasErrors()) {

            throw UserException.builder()
                    .errorType(ErrorType.EMAIL_BAD_FORMAT)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .errorMessage(bindingResult.getFieldError().getDefaultMessage())
                    .build();
        }

        return userService.saveSocialUser(userRegisterDto);
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<String> updateUser(@Valid @RequestBody ForgotPasswordDto forgotPasswordDto, BindingResult bindingResult) throws Exception, UserException {
        if (bindingResult.hasErrors()) {
            throw UserException.builder()
                    .errorType(ErrorType.DAO_BAD_FORMAT)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .errorMessage(bindingResult.getFieldError().getDefaultMessage())
                    .build();
        }

        return userService.updateSocialUser(forgotPasswordDto);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserLoginDto userLoginDto, BindingResult bindingResult) throws UserException {
        if (bindingResult.hasErrors()) {
            throw UserException.builder()
                    .errorType(ErrorType.DAO_BAD_FORMAT)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .errorMessage(bindingResult.getFieldError().getDefaultMessage())
                    .build();
        }

        return userService.authenticate(userLoginDto);
    }
}
