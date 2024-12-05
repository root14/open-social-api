package com.root14.opensocialapi.controller;

import com.root14.opensocialapi.dto.TestUserPost;
import com.root14.opensocialapi.entity.Post;
import com.root14.opensocialapi.entity.User;
import com.root14.opensocialapi.repository.UserRepository;
import com.root14.opensocialapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("api/test")
@RequiredArgsConstructor
public class TestController {

    private final UserRepository userRepository;


    @PostMapping("/addPost")
    public ResponseEntity<String> addPost(@RequestBody TestUserPost testUserPost) {
        Optional<User> test_user = userRepository.getUserByUsername(testUserPost.getTestUserId());

        Post post = new Post();
        post.setContent(testUserPost.getTestContent());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        User user;

        if (test_user.isPresent()) {
            user = test_user.get();
        } else {
            user = new User();
            user.setId(Long.valueOf(testUserPost.getTestUserId()));
            user.setPassword("test-user-cannot-login");
            user.setUsername(testUserPost.getTestUserName());
            user.setEmail(testUserPost.getTestUserName() + "@test.com");
            user.setCreatedAt(LocalDateTime.now());
            user.setUpdatedAt(LocalDateTime.now());
            post.setUser(user);

        }
        user.addPost(post);
        userRepository.save(user);
        return ResponseEntity.ok(post.toString());
    }
}
