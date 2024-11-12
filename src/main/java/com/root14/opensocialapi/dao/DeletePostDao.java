package com.root14.opensocialapi.dao;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeletePostDao {
    private Long postId;
    private Long authorId;
}
