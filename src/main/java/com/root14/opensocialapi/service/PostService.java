package com.root14.opensocialapi.service;

import com.root14.opensocialapi.dao.AddPostDao;
import com.root14.opensocialapi.dao.DeletePostDao;
import com.root14.opensocialapi.dao.UpdatePostDao;
import com.root14.opensocialapi.entity.Post;
import com.root14.opensocialapi.entity.User;
import com.root14.opensocialapi.exception.ErrorType;
import com.root14.opensocialapi.exception.PostException;
import com.root14.opensocialapi.repository.PostRepository;
import com.root14.opensocialapi.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PostService {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    public PostService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    //TODO add user check
    public ResponseEntity<String> updatePost(UpdatePostDao updatePostDao) {
        Optional<Post> optionalPost = postRepository.findPostById(updatePostDao.getPostId());

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setContent(updatePostDao.getPost().getContent());
            post.setUpdatedAt(LocalDateTime.now());
            postRepository.save(post);
            return ResponseEntity.ok().body("Post updated");
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Post not found");
    }

    //TODO add user check
    public ResponseEntity<String> deletePost(DeletePostDao deletePostDao) {
        postRepository.deleteById(deletePostDao.getPostId());
        return new ResponseEntity<>("Post deleted", HttpStatus.OK);
    }

    //TODO add user check
    public ResponseEntity<String> savePost(AddPostDao addPostDao) throws PostException {
        Optional<User> optionalUser = userRepository.getUserByUsername(addPostDao.getUserName());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Post post = new Post();

            post.setUser(user);
            post.setAuthorId(user.getId());
            post.setCreatedAt(LocalDateTime.now());
            post.setUpdatedAt(LocalDateTime.now());
            post.setContent(addPostDao.getContent());

            user.addPost(post);

            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.CREATED).body("Post saved.");
        } else {
            throw PostException.builder()
                    .errorMessage("User not found.")
                    .errorType(ErrorType.USER_NOT_FOUND)
                    .httpStatus(HttpStatus.NOT_FOUND)
                    .build();
        }
    }
}
