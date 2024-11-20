package com.root14.opensocialapi.controller;

import com.root14.opensocialapi.dao.AddPostDao;
import com.root14.opensocialapi.dao.DeletePostDao;
import com.root14.opensocialapi.dao.LikePostDao;
import com.root14.opensocialapi.dao.UpdatePostDao;
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
    public ResponseEntity<String> addPost(@RequestBody AddPostDao addPostDao) throws PostException {
        return postService.savePost(addPostDao);
    }

    @DeleteMapping("/deletePost")
    public ResponseEntity<String> deletePost(@RequestBody DeletePostDao deletePostDao) throws PostException {
        return postService.deletePost(deletePostDao);
    }

    @PatchMapping("/patchPost")
    public ResponseEntity<String> patchPost(@RequestBody UpdatePostDao updatePostDao) throws PostException {
        return postService.patchPost(updatePostDao);
    }

    @PostMapping("/likedPost")
    public ResponseEntity<String> likedPost(@RequestBody LikePostDao likePostDao) throws PostException {
        return postService.addPostLikedUser(likePostDao);
    }

    @GetMapping
    public void getTimelinePost() {

    }
}
