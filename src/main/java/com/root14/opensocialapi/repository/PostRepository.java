package com.root14.opensocialapi.repository;

import com.root14.opensocialapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findPostById(Long id);

    Boolean deletePostById(Long id);

    Optional<List<Post>> findTop20ByOrderByCreatedAtDesc();


}
