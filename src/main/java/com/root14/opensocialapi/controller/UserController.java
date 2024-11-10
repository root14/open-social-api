package com.root14.opensocialapi.controller;

import com.root14.opensocialapi.dao.UserRegisterDao;
import com.root14.opensocialapi.dao.UserUpdateDao;
import com.root14.opensocialapi.entity.User;
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

    @GetMapping("/getAllUser")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(users);
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody UserRegisterDao userRegisterDao, BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()) {
            //TODO
            throw new Exception(bindingResult.getFieldError().getDefaultMessage());
        }

        return userService.saveSocialUser(userRegisterDao);
    }

    @PostMapping("/update")
    public ResponseEntity<String> updateUser(@Valid @RequestBody UserUpdateDao userUpdateDao, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors()) {
            throw new Exception(bindingResult.getFieldError().getDefaultMessage());
        }

        return userService.updateSocialUser(userUpdateDao);
    }

}
