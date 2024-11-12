package com.root14.opensocialapi.repository;

import com.root14.opensocialapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    Optional<Post> findPostByAuthorId(Long authorId);

    Optional<Post> findPostById(Long id);

    Optional<List<Post>> findPostsByAuthorId(Long authorId);

    Boolean deletePostById(Long id);
}
