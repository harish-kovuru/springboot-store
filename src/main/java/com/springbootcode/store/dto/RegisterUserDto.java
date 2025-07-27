package com.springbootcode.store.dto;

import com.springbootcode.store.validations.Lowercase;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserDto {
    @NotBlank(message = "Name is required")
    @Size(max = 255, message = "max length is 255")
    private String name;

    @NotBlank(message = "email is required")
    @Email(message = "invalid email")
    @Lowercase(message = "email must be lowercase")
    private String email;

    @NotBlank(message = "password cant be empty")
    @Size(min = 6, max = 10, message = "password should be between 6 to 10")
    private String password;
}
