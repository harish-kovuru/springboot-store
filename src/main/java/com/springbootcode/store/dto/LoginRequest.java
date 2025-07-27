package com.springbootcode.store.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "email cant be empty")
    @Email
    private String email;
    @NotBlank(message = "password not provided")
    private String password;
}
