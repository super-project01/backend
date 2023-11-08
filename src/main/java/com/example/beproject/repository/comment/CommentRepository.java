package com.example.beproject.repository.comment;

import com.example.beproject.domain.comment.Comment;
import com.example.beproject.entity.comment.CommentEntity;
import com.example.beproject.service.CommentService.CommentNotFoundException;

import java.util.List;
import java.util.Optional;

public interface CommentRepository {

    CommentEntity save(Comment comment);

    Optional<Comment> findById(Long id);

    CommentEntity update(CommentEntity commentEntity) throws CommentNotFoundException;

    void delete(Long id);

    List<CommentEntity> findAll();
}

