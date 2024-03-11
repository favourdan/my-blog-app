package com.favourite.blogapp.dto;

import com.favourite.blogapp.entity.Comments;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PostResponseDto {
    private Long id;
    private String title;
    private String description;
    private String content;
    private Set<CommentResponseDto> comments;
}
