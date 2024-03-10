package com.favourite.blogapp.service.serviceImpl;

import com.favourite.blogapp.dto.PostDto;
import com.favourite.blogapp.dto.PostResCusDto;
import com.favourite.blogapp.dto.PostResponseDto;
import com.favourite.blogapp.entity.Post;

import java.util.List;

public interface PostService {

    PostResponseDto addPost(PostDto postDto);

   PostResCusDto getAllPost(int pageSize , int pageNo, String sortBy , String sortDir);
    PostResponseDto getPostId(Long postId);

    PostResponseDto updatePost(PostDto postDto, Long postId);

    String deletePost(Long id);
}
