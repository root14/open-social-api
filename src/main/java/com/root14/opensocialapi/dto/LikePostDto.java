package com.root14.opensocialapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LikePostDto {
    private Long postId;
    /**
     * for like true,
     * for dislike false
     */
    private Long like;
}