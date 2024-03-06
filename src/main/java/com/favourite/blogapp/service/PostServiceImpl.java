package com.favourite.blogapp.service;

import com.favourite.blogapp.dto.PostDto;
import com.favourite.blogapp.dto.PostResponseDto;
import com.favourite.blogapp.entity.Post;
import com.favourite.blogapp.exception.AlreadyExitException;
import com.favourite.blogapp.exception.ResourceNotFound;
import com.favourite.blogapp.repository.PostRepository;
import com.favourite.blogapp.service.serviceImpl.PostService;
import lombok.*;
import org.springframework.stereotype.Service;

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

    @Override
    public PostResponseDto getPostId(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFound("post","id", postId));
        PostResponseDto postResponseDto = new PostResponseDto();
        postResponseDto.setId(post.getId());
        postResponseDto.setTitle(post.getTitle());
        postResponseDto.setDescription(post.getDescription());
        postResponseDto.setContent(post.getContent());
        return postResponseDto;
    }

    @Override
    public PostResponseDto updatePost(PostDto postDto, Long postId) {
        Post newPost = postRepository.findById(postId).orElseThrow(()-> new ResourceNotFound("post","id", postId));
//        checking Null values before updating a post
        if(postDto.getTitle() != null && !postDto.getTitle() .isEmpty()){
            newPost.setTitle(postDto.getTitle());
        }
        if(postDto.getDescription() != null && !postDto.getDescription().isEmpty()){
            newPost.setDescription(postDto.getDescription());
        }
        if(postDto.getContent() != null && !postDto.getContent().isEmpty()){
            newPost.setContent(postDto.getContent());
        }
        Post updatedPost= postRepository.save(newPost);

        PostResponseDto postResponseDto = new PostResponseDto();
        postResponseDto.setId(updatedPost.getId());
        postResponseDto.setTitle(updatedPost.getTitle());
        postResponseDto.setDescription(updatedPost.getDescription());
        postResponseDto.setContent(updatedPost.getContent());

        return postResponseDto;
    }

    @Override
    public String deletePost(Long id) {
        Post newPost = postRepository.findById(id).orElseThrow(()-> new ResourceNotFound("post","id", id));
        postRepository.delete(newPost);
        return "post with id: " + id + "has been successfully deleted" ;
    }


}
