package com.root14.opensocialapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TestUserPost {
    private String testUserId;
    private String testContent;
    private String testUserName;
}
