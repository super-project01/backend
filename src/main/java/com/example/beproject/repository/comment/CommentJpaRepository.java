package com.example.beproject.repository.comment;

import com.example.beproject.entity.comment.CommentEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentJpaRepository extends JpaRepository<CommentEntity, Long> {
}
