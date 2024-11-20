package com.root14.opensocialapi.dao;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddPostDao {
    private String content;
}
