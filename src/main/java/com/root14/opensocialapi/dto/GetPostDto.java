package com.root14.opensocialapi.dto;


import com.root14.opensocialapi.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPostDto {
    private String author_name;
    private Post post;
}
