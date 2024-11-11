package com.root14.opensocialapi.controller;

import com.root14.opensocialapi.dao.UserLoginDao;
import com.root14.opensocialapi.dao.UserRegisterDao;
import com.root14.opensocialapi.dao.ForgotPasswordDao;
import com.root14.opensocialapi.entity.User;
import com.root14.opensocialapi.exception.ErrorType;
import com.root14.opensocialapi.exception.UserException;
import com.root14.opensocialapi.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //todo add has role admin
    @GetMapping("/getAllUser")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegisterDao userRegisterDao, BindingResult bindingResult) throws UserException {

        if (bindingResult.hasErrors()) {
            UserException userException = UserException.builder()
                    .errorType(ErrorType.EMAIL_BAD_FORMAT)
                    .httpStatus(HttpStatus.BAD_REQUEST)
                    .errorMessage(bindingResult.getFieldError().getDefaultMessage())
                    .build();

            throw userException;
        }

        return userService.saveSocialUser(userRegisterDao);
    }

    @PostMapping("/forgotPassword")
    public ResponseEntity<String> updateUser(@Valid @RequestBody ForgotPasswordDao forgotPasswordDao, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new Exception(bindingResult.getFieldError().getDefaultMessage());
        }

        return userService.updateSocialUser(forgotPasswordDao);
    }

    public ResponseEntity<String> login(@RequestBody UserLoginDao userLoginDao, BindingResult bindingResult) throws Exception {
        //TODO update this fun on when spring security implemented
        if (bindingResult.hasErrors()) {
            throw new Exception(bindingResult.getFieldError().getDefaultMessage());
        }

        return null;
    }
}
