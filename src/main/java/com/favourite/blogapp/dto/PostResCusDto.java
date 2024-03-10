package com.favourite.blogapp.dto;

import com.favourite.blogapp.entity.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PostResCusDto {
    private List<PostResponseDto> content;
    private int pageSize;
    private int pageNo;
    private Long totalContent;
    private int totalPages;
    private boolean finalPage;

}
