package com.example.beproject.repository.comment;

import com.example.beproject.domain.comment.Comment;
import com.example.beproject.domain.comment.UpdateComment;
import com.example.beproject.exception.CommentNotFoundException;
import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    Optional<Comment> findById(Long id);

    Comment update(Long id, UpdateComment updateComment) throws CommentNotFoundException;

    void delete(Long id);

    List<Comment> findAll();

    Comment getComment(Long id);

}