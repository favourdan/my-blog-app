package com.favourite.blogapp.service;

import com.favourite.blogapp.dto.CommentResponseDto;
import com.favourite.blogapp.dto.PostDto;
import com.favourite.blogapp.dto.PostResCusDto;
import com.favourite.blogapp.dto.PostResponseDto;
import com.favourite.blogapp.entity.Post;
import com.favourite.blogapp.exception.AlreadyExitException;
import com.favourite.blogapp.exception.ResourceNotFound;
import com.favourite.blogapp.repository.PostRepository;
import com.favourite.blogapp.service.serviceImpl.PostService;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
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
    public PostResCusDto getAllPost(int pageSize, int pageNo, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageSize,pageNo, sort);
        Page<Post> posts = postRepository.findAll(pageable);
        List<Post> postContent = posts.getContent();
        List<PostResponseDto> allPost = postContent.stream().map(post ->{
            PostResponseDto postResponseDto = new PostResponseDto();
            postResponseDto.setId(post.getId());
            postResponseDto.setTitle(post.getTitle());
            postResponseDto.setDescription(post.getDescription());
            postResponseDto.setContent(post.getContent());
            Set<CommentResponseDto> commentResponseDtos = post.getComments().stream().map(comment ->{
                CommentResponseDto commentResponseDto = new CommentResponseDto();
                commentResponseDto.setId(comment.getId());
                commentResponseDto.setName(comment.getName());
                commentResponseDto.setEmail(comment.getEmail());
                commentResponseDto.setBody(comment.getBody());
                return commentResponseDto;
            }).collect(Collectors.toSet());

            postResponseDto.setComments(commentResponseDtos);
            return postResponseDto;
        }).collect(Collectors.toList());

        PostResCusDto postResCusDto = new PostResCusDto();
        postResCusDto.setContent(allPost);
        postResCusDto.setPageNo(posts.getNumber());
        postResCusDto.setPageSize(posts.getSize());
        postResCusDto.setTotalContent(posts.getTotalElements());
        postResCusDto.setTotalPages(posts.getTotalPages());
        postResCusDto.setFinalPage(posts.isLast());
        return postResCusDto;
    }


    // GetAllPost using Pagination and sorting
//    @Override
//    public PostResCusDto getAllPost(int pageSize, int pageNo, String sortBy) {
//        Pageable pageable = PageRequest.of(pageSize,pageNo, Sort.by(sortBy));
//        Page<Post> posts = postRepository.findAll(pageable);
//        List<Post> postContent = posts.getContent();
//        List<PostResponseDto> allPost = postContent.stream().map(post ->{
//            PostResponseDto postResponseDto = new PostResponseDto();
//            postResponseDto.setId(post.getId());
//            postResponseDto.setTitle(post.getTitle());
//            postResponseDto.setDescription(post.getDescription());
//            postResponseDto.setContent(post.getContent());
//            return postResponseDto;
//        }).collect(Collectors.toList());
//        PostResCusDto postResCusDto = new PostResCusDto();
//        postResCusDto.setContent(allPost);
//        postResCusDto.setPageNo(posts.getNumber());
//        postResCusDto.setPageSize(posts.getSize());
//        postResCusDto.setTotalContent(posts.getTotalElements());
//        postResCusDto.setTotalPages(posts.getTotalPages());
//        postResCusDto.setFinalPage(posts.isLast());
//        return postResCusDto;
//    }
       // GetAllPost
//    @Override
//    public List<PostResponseDto> getAllPost() {
//        List<Post> posts = postRepository.findAll();
//        System.out.println("post" + posts );
//        List<PostResponseDto> postResponseDto = posts.stream().map(post -> {
//            PostResponseDto postResponse = new PostResponseDto();
//            postResponse.setId(post.getId());
//            postResponse.setTitle(post.getTitle());
//            postResponse.setDescription(post.getDescription());
//            postResponse.setContent(post.getContent());
//            return postResponse;
//        }).collect(Collectors.toList());
//        return postResponseDto;
//    }

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
