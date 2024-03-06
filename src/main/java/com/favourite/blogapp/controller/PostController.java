package com.favourite.blogapp.controller;

import com.favourite.blogapp.dto.PostDto;
import com.favourite.blogapp.dto.PostResponseDto;
import com.favourite.blogapp.entity.Post;
import com.favourite.blogapp.service.PostServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class PostController {
    private final PostServiceImpl postService;
@PostMapping("/posts")
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostDto postDto){
       return new ResponseEntity<>(postService.addPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping("/posts")
    public List<PostResponseDto> getAllPost(){
       return postService.getAllPost();
    }
@GetMapping("/posts/{id}")
    public ResponseEntity<PostResponseDto> getPostId(@PathVariable("id") Long postId){
           return new ResponseEntity<>(postService.getPostId(postId),HttpStatus.OK);
    }
    @PutMapping("posts/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@RequestBody PostDto postDto , @PathVariable("id") Long postId){
       PostResponseDto postResponseDto = postService.updatePost(postDto,postId);
       return new ResponseEntity<>(postResponseDto,HttpStatus.OK);
    }
    @DeleteMapping("post/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id){
    String post = postService.deletePost(id);
    return new ResponseEntity<>(post, HttpStatus.OK);
    }
}
