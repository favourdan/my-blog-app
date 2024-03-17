package com.favourite.blogapp.dto;

import jakarta.validation.constraints.Email;
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
public class SignUpDto {
    @NotEmpty(message = "name must not be empty")
    private String name;
    @NotEmpty(message = "userName must not be empty")
    private String userName;
    @Email
    @NotEmpty(message = "email must not be empty")
    private String email;
    @Size(min = 5 , message = "password should not be less than five")
    @NotEmpty(message = "password should not be empty or null")
    private String password;

}
