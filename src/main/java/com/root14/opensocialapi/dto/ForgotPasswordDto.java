package com.root14.opensocialapi.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ForgotPasswordDto {
    @Size(min = 8, max = 20)
    private String password;

    @Email
    private String email;

    private String userName;
}
