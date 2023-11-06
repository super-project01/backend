package com.example.beproject.repository.comment;

import com.example.beproject.domain.comment.Comment;
import com.example.beproject.entity.comment.CommentEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository commentJPARepository;

    }
}
