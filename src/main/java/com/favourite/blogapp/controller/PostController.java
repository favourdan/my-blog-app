package com.favourite.blogapp.controller;

import com.favourite.blogapp.dto.PostDto;
import com.favourite.blogapp.dto.PostResCusDto;
import com.favourite.blogapp.dto.PostResponseDto;
import com.favourite.blogapp.service.PostServiceImpl;
import com.favourite.blogapp.service.serviceImpl.PostService;
import com.favourite.blogapp.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class PostController {
    private final PostService postService;
@PostMapping("/post")
    public ResponseEntity<PostResponseDto> createPost(@RequestBody PostDto postDto){
       return new ResponseEntity<>(postService.addPost(postDto), HttpStatus.CREATED);
    }

//    @GetMapping("/posts")
//    public List<PostResponseDto> getAllPost(){
//       return postService.getAllPost();
//    }

//    @GetMapping("/posts")
//    public List<PostResponseDto> getAllPost(@RequestParam(value = "pageNo",defaultValue = "0",required = false) int pageNo,
//                                            @RequestParam (value = "pageSize",defaultValue = "10",required = false) int pageSize){
//        return postService.getAllPost(pageNo,pageSize);
//    }

    @GetMapping("/posts")
    public PostResCusDto getAllPost(@RequestParam(value = "pageNo",defaultValue = Constants.PAGE_NO,required = false) int pageNo,
                                    @RequestParam (value = "pageSize",defaultValue = Constants.PAGE_SIZE,required = false) int pageSize,
                                    @RequestParam (value = "sortBy",defaultValue = Constants.SORT_BY,required = false) String sortBy,
                                    @RequestParam (value = "sortDir",defaultValue = Constants.SORT_DIR,required = false) String sortDir){
        return postService.getAllPost(pageNo,pageSize,sortBy,sortDir);
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
    @DeleteMapping("posts/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id){
    String post = postService.deletePost(id);
    return new ResponseEntity<>(post, HttpStatus.OK);
    }
}
