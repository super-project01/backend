package com.example.beproject.service.CommentService;

import com.example.beproject.domain.comment.Comment;
import com.example.beproject.domain.comment.CreateComment;
import com.example.beproject.domain.comment.UpdateComment;
import com.example.beproject.entity.comment.CommentEntity;
import com.example.beproject.exception.CommentException;

import java.util.List;

public interface CommentService {
    List<Comment> getAllComments();

    Comment getComment(Long id) throws CommentException.CommentNotFoundException;

    Comment createComment(CreateComment createComment);

    Comment updateComment(Long id, UpdateComment updateComment) throws CommentException.CommentNotFoundException;

    void deleteComment(Long id);

}
