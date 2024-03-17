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
public class PostDto {
    @NotEmpty
    @Size(min = 2, message = "post title should at least have 2 characters ")
    private String title;
    @NotEmpty
    @Size(min = 5, message = "post description should have more than 5 characters")
    private String description;
    @NotEmpty
    @Size(min = 10, message = "post content should have more than 10 characters")
    private String content;
}
