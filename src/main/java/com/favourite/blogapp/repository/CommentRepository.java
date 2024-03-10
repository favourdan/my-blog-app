package com.favourite.blogapp.repository;

import com.favourite.blogapp.dto.CommentResponseDto;
import com.favourite.blogapp.entity.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository  extends JpaRepository<Comments , Long > {

    List<Comments> getCommentsByPostId(Long postId);

}
