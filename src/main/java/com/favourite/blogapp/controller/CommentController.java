package com.favourite.blogapp.controller;

import com.favourite.blogapp.dto.CommentRequestDto;
import com.favourite.blogapp.dto.CommentResponseDto;
import com.favourite.blogapp.service.serviceImpl.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;
    @PostMapping("post/{postId}/comment")
    public ResponseEntity<CommentResponseDto> createComment(@PathVariable Long postId, @Valid @RequestBody CommentRequestDto commentRequestDto){
        return new ResponseEntity<>(commentService.createComment(commentRequestDto,postId), HttpStatus.CREATED);
    }

    @GetMapping("post/{postId}/comment")
    public List<CommentResponseDto> getAllcomments(@PathVariable Long postId){
        return commentService.getAllComment(postId);
    }

    @GetMapping("post/{postId}/comment/{commentId}")
    public CommentResponseDto getCommentById(@PathVariable Long postId ,@PathVariable Long commentId){
        return commentService.getCommentById(postId , commentId);
    }
    @PutMapping("post/{postId}/comment/{commentId}")
    public ResponseEntity<CommentResponseDto> updateComment(@PathVariable Long postId ,@PathVariable Long commentId,@Valid @RequestBody CommentRequestDto commentRequestDto){
        return ResponseEntity.ok(commentService.updateComment(postId,commentId,commentRequestDto));
    }

    @DeleteMapping("post/{postId}/comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long postId ,@PathVariable Long commentId){
        return ResponseEntity.ok(commentService.deleteComment(postId,commentId));
    }
}
