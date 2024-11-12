package com.root14.opensocialapi.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/post")
public class PostController {


    @PostMapping("/addPost")
    public void addPost() {

    }

    @DeleteMapping("deletePost")
    public void deletePost() {

    }

    @PatchMapping
    public void patchPost() {

    }

    @GetMapping
    public void getTimelinePost() {

    }
}
