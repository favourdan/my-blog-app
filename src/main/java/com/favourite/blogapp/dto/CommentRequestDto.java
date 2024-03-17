package com.favourite.blogapp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CommentRequestDto {
@NotEmpty(message = "name should not be empty or null")
    private String name;
@NotEmpty(message = "email should not be empty or null")
@Email
    private String email;
@NotEmpty(message = "body should not be empty or null ")
    private String body;
}
