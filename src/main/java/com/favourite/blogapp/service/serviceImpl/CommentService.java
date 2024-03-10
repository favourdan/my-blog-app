package com.favourite.blogapp.service.serviceImpl;

import com.favourite.blogapp.dto.CommentRequestDto;
import com.favourite.blogapp.dto.CommentResponseDto;

import java.util.List;

public interface CommentService {
    CommentResponseDto createComment(CommentRequestDto commentRequestDto,Long postId);

    List<CommentResponseDto> getAllComment(Long postId);

    CommentResponseDto getCommentById(Long postId, Long commentId);

    CommentResponseDto updateComment(Long postId, Long commentId, CommentRequestDto commentRequestDto);

    String deleteComment(Long postId, Long commentId);
}
