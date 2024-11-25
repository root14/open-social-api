package com.root14.opensocialapi.controller;

import com.root14.opensocialapi.dto.AddPostDto;
import com.root14.opensocialapi.dto.DeletePostDto;
import com.root14.opensocialapi.dto.LikePostDto;
import com.root14.opensocialapi.dto.UpdatePostDto;
import com.root14.opensocialapi.exception.PostException;
import com.root14.opensocialapi.service.PostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
public class PostController {
    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/addPost")
    public ResponseEntity<String> addPost(@RequestBody AddPostDto addPostDto) throws PostException {
        return postService.savePost(addPostDto);
    }

    @DeleteMapping("/deletePost")
    public ResponseEntity<String> deletePost(@RequestBody DeletePostDto deletePostDto) throws PostException {
        return postService.deletePost(deletePostDto);
    }

    @PatchMapping("/patchPost")
    public ResponseEntity<String> patchPost(@RequestBody UpdatePostDto updatePostDto) throws PostException {
        return postService.patchPost(updatePostDto);
    }

    @PostMapping("/likePost")
    public ResponseEntity<String> likePost(@RequestBody LikePostDto likePostDto) throws PostException {
        return postService.addPostLikeUser(likePostDto);
    }

    @PostMapping
    public ResponseEntity<Integer> getPostLikeCount(@RequestBody LikePostDto likePostDto) throws PostException {
        return postService.getPostLikeCount(likePostDto);
    }

    @GetMapping
    public void getTimelinePost() {

    }
}
