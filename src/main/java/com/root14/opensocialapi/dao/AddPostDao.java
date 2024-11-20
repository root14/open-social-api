package com.root14.opensocialapi.dao;

import jakarta.validation.constraints.Email;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddPostDao {
    @Email
    private String userName;
    private String content;
}
