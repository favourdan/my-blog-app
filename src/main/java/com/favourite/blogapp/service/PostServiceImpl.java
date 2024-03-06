package com.favourite.blogapp.service;

import com.favourite.blogapp.dto.PostDto;
import com.favourite.blogapp.dto.PostResponseDto;
import com.favourite.blogapp.entity.Post;
import com.favourite.blogapp.exception.AlreadyExitException;
import com.favourite.blogapp.repository.PostRepository;
import com.favourite.blogapp.service.serviceImpl.PostService;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    @Override
    public PostResponseDto addPost(PostDto postDto) {

        if(postRepository.existsByTitle(postDto.getTitle()))
            throw new AlreadyExitException("Post with title " + postDto.getTitle() + " already exists");
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        Post newPost = postRepository.save(post);

        PostResponseDto postResponseDto = new PostResponseDto();
        postResponseDto.setId(newPost.getId());
        postResponseDto.setTitle(newPost.getTitle());
        postResponseDto.setDescription(newPost.getDescription());
        postResponseDto.setContent(newPost.getContent());
        return postResponseDto ;
    }

    @Override
    public List<PostResponseDto> getAllPost() {
        List<Post> posts = postRepository.findAll();
        System.out.println("post" + posts );
        List<PostResponseDto> postResponseDto = posts.stream().map(post -> {
            PostResponseDto postResponse = new PostResponseDto();
            postResponse.setId(post.getId());
            postResponse.setTitle(post.getTitle());
            postResponse.setDescription(post.getDescription());
            postResponse.setContent(post.getContent());
            return postResponse;
        }).collect(Collectors.toList());
        return postResponseDto;
    }


}
