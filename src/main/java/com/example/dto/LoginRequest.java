package com.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    @NotBlank(message = "Username  can not be null or empty")
    String username;

    @Email(message = "Email  can not be null or empty")
    String email;

    @NotBlank(message = "Password  can not be null or empty")
    String password;
}
