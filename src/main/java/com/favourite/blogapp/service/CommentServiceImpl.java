package com.favourite.blogapp.service;

import com.favourite.blogapp.dto.CommentRequestDto;
import com.favourite.blogapp.dto.CommentResponseDto;
import com.favourite.blogapp.entity.Comments;
import com.favourite.blogapp.entity.Post;
import com.favourite.blogapp.exception.ApiException;
import com.favourite.blogapp.exception.ResourceNotFound;
import com.favourite.blogapp.repository.CommentRepository;
import com.favourite.blogapp.repository.PostRepository;
import com.favourite.blogapp.service.serviceImpl.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;


    @Override
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, Long postId) {
        Comments comment = new Comments();
        comment.setName(commentRequestDto.getName());
        comment.setEmail(commentRequestDto.getEmail());
        comment.setBody(commentRequestDto.getBody());

        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFound("post","id",postId));
        comment.setPost(post);
        Comments comments = commentRepository.save(comment);
        CommentResponseDto commentResponseDto = new CommentResponseDto();
        commentResponseDto.setId(comments.getId());
        commentResponseDto.setName(comments.getName());
        commentResponseDto.setEmail(comments.getEmail());
        commentResponseDto.setBody(comments.getBody());
        return commentResponseDto;
    }

    @Override
    public List<CommentResponseDto> getAllComment(Long postId) {
        List<Comments> comments = commentRepository.getCommentsByPostId(postId);
        List<CommentResponseDto>commentResponseDtos = comments.stream().map(comment ->{
            CommentResponseDto commentResponseDto = new CommentResponseDto();
            commentResponseDto.setId(comment.getId());
            commentResponseDto.setName(comment.getName());
            commentResponseDto.setEmail(comment.getEmail());
            commentResponseDto.setBody(comment.getBody());
            return commentResponseDto;
        }).collect(Collectors.toList());
        return commentResponseDtos;
    }

    @Override
    public CommentResponseDto getCommentById(Long postId, Long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFound("post","id",postId));
        log.info("Fetching post with ID: {}", postId);
        Comments comments = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFound("comment",
                "commentId", commentId));
        if(!comments.getPost().getId().equals(post.getId())){
            throw new ApiException(HttpStatus.BAD_REQUEST,"comment does not not belong to the post");
        }

        CommentResponseDto commentResponseDto = new CommentResponseDto();
        commentResponseDto.setId(comments.getId());
        commentResponseDto.setName(comments.getName());
        commentResponseDto.setEmail(comments.getEmail());
        commentResponseDto.setBody(comments.getBody());
        return commentResponseDto;
    }

    @Override
    public CommentResponseDto updateComment(Long postId, Long commentId, CommentRequestDto commentRequestDto) {
        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFound("post","id",postId));
        Comments comments = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFound("comment",
                "commentId", commentId));
        if(!comments.getPost().getId().equals(post.getId())){
            throw new ApiException(HttpStatus.BAD_REQUEST,"comment does not not belong to the post");
        }
        comments.setName(commentRequestDto.getName());
        comments.setEmail(commentRequestDto.getEmail());
        comments.setBody(commentRequestDto.getBody());

        Comments updatedComments = commentRepository.save(comments);

        CommentResponseDto commentResponseDto = new CommentResponseDto();
        commentResponseDto.setId(updatedComments.getId());
        commentResponseDto.setName(updatedComments.getName());
        commentResponseDto.setEmail(updatedComments.getEmail());
        commentResponseDto.setBody(updatedComments.getBody());
        return commentResponseDto;
    }

    @Override
    public String deleteComment(Long postId, Long commentId) {
        Post posts = postRepository.findById(postId).orElseThrow(()->new ResourceNotFound("post","id",postId));
        Comments comments = commentRepository.findById(commentId).orElseThrow(()-> new ResourceNotFound("comment",
                "commentId", commentId));
        if(!comments.getPost().getId().equals(posts.getId())){
            throw new ApiException(HttpStatus.BAD_REQUEST,"comment does not not belong to the post");
        }
        commentRepository.delete(comments);
        return "Comment with post Id "+ postId + "has been deleted";
    }
}
