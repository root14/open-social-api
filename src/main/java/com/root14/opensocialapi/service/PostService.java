package com.root14.opensocialapi.service;

import com.root14.opensocialapi.dto.*;
import com.root14.opensocialapi.entity.Post;
import com.root14.opensocialapi.entity.User;
import com.root14.opensocialapi.exception.ErrorType;
import com.root14.opensocialapi.exception.PostException;
import com.root14.opensocialapi.repository.PostRepository;
import com.root14.opensocialapi.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public PostService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    //return last 20 post, it`s not real random timeline post
    public ResponseEntity<List<PostDto>> getTimelinePost() {
        //authenticated(jwt) userName
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<List<Post>> randomList = postRepository.findTop20ByOrderByCreatedAtDesc();

        if (randomList.isPresent()) {
            List<Post> posts = randomList.get();
            List<PostDto> postDto = new ArrayList<>();

            for (Post post : posts) {
                PostDto _postDto = new PostDto();
                _postDto.setAuthorName(post.getUser().getUsername());
                _postDto.setContent(post.getContent());
                _postDto.setComments(post.getComments());
                _postDto.setCreatedAt(post.getCreatedAt());
                _postDto.setUpdatedAt(post.getUpdatedAt());
                postDto.add(_postDto);
            }
            return ResponseEntity.status(HttpStatus.OK).body(postDto);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    public ResponseEntity<String> patchPost(UpdatePostDto updatePostDto) {
        //authenticated(jwt) userName
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Post> optionalPost = postRepository.findPostById(updatePostDto.getPostId());

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            if (userName.equals(post.getUser().getUsername())) {
                post.setContent(updatePostDto.getPost().getContent());
                post.setUpdatedAt(LocalDateTime.now());
                postRepository.save(post);
                return ResponseEntity.ok().body("Post updated");
            }
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
    }

    public ResponseEntity<String> deletePost(DeletePostDto deletePostDto) throws PostException {
        //authenticated(jwt) userId
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Post> post = postRepository.findPostById(deletePostDto.getPostId());

        if (post.isPresent()) {
            if (userName.equals(post.get().getUser().getUsername())) {
                postRepository.delete(post.get());
                return new ResponseEntity<>("Post deleted", HttpStatus.OK);
            }
        } else {
            throw PostException.builder().httpStatus(HttpStatus.NOT_MODIFIED).errorType(ErrorType.NOT_CHANGED).errorMessage("Post cannot find.").build();
        }
        throw PostException.builder().httpStatus(HttpStatus.NOT_MODIFIED).errorType(ErrorType.NOT_CHANGED).errorMessage("Cannot delete.").build();
    }

    public ResponseEntity<String> savePost(AddPostDto addPostDto) throws PostException {
        //authenticated(jwt) userId
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<User> optionalUser = userRepository.getUserByUsername(userName);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Post post = new Post();

            post.setUser(user);
            post.setCreatedAt(LocalDateTime.now());
            post.setUpdatedAt(LocalDateTime.now());
            post.setContent(addPostDto.getContent());
            user.addPost(post);

            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("Post saved.");
        } else {
            throw PostException.builder().errorMessage("User not found.").errorType(ErrorType.USER_NOT_FOUND).httpStatus(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<String> addPostLikeUser(LikePostDto likePostDto) throws PostException {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Post> post = postRepository.findPostById(likePostDto.getPostId());
        if (post.isPresent()) {
            Post foundedPost = post.get();
            foundedPost.getLikedUsersId().add(userName);
            return ResponseEntity.status(HttpStatus.OK).body("Post liked.");
        } else {
            throw PostException.builder().errorMessage("Post not found.").errorType(ErrorType.POST_NOT_FOUND).httpStatus(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<GetPostDto> getPostById(Long postId) throws PostException {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Post> optionalPost = postRepository.findPostById(postId);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            GetPostDto getPostDto = new GetPostDto(post.getUser().getUsername(), post);
            return ResponseEntity.status(HttpStatus.OK).body(getPostDto);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    public ResponseEntity<Integer> getPostLikeCount(LikePostDto likePostDto) throws PostException {
        String userName = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Post> post = postRepository.findPostById(likePostDto.getPostId());
        if (post.isPresent()) {
            Post foundedPost = post.get();
            return ResponseEntity.ok(foundedPost.getLikedUsersId().size());
        }
        throw PostException.builder().errorMessage("Post not found.").errorType(ErrorType.POST_NOT_FOUND).httpStatus(HttpStatus.NOT_FOUND).build();
    }
}
