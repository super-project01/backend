package com.example.beproject.repository.comment;

import com.example.beproject.domain.comment.Comment;
import com.example.beproject.exception.CommentException;
import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    Comment save(Comment comment);

    Optional<Comment> findById(Long id);

    Comment update(Comment comment) throws CommentException.CommentNotFoundException;

    void delete(Long id);

    List<Comment> findAll();
}

