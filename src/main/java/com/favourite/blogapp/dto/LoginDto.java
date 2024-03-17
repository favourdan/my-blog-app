package com.favourite.blogapp.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LoginDto {
    @NotEmpty(message = "usernameOremail should not null or empty")
    private String userNameOrEmail;
    @NotEmpty(message = "password should not be null or empty")
    @Size(min=2, message = "password should not be less than two(2)")
    private String password;
}
