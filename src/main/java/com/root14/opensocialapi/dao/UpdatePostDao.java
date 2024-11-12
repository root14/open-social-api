package com.root14.opensocialapi.dao;

import com.root14.opensocialapi.entity.Post;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdatePostDao {
    private Long postId;
    private AddPostDao post;
}
