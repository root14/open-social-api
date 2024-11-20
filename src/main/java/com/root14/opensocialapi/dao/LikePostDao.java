package com.root14.opensocialapi.dao;

import lombok.Data;

@Data
public class LikePostDao {
    private Long postId;
    /**
     * for like true,
     * for dislike false
     */
    private Long like;
}
