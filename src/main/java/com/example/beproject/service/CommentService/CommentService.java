package com.example.beproject.service.CommentService;

import com.example.beproject.domain.comment.Comment;
import com.example.beproject.domain.comment.CreateComment;
import com.example.beproject.domain.comment.UpdateComment;
import com.example.beproject.exception.CommentNotFoundException;
import java.util.List;

public interface CommentService {
    List<Comment> getAllComments();

    Comment getComment(Long id) throws CommentNotFoundException;

    Comment createComment(CreateComment createComment);

    Comment updateComment(Long id, UpdateComment updateComment) throws CommentNotFoundException;

    void deleteComment(Long id);
}