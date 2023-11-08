package com.example.beproject.repository.comment;

import com.example.beproject.domain.comment.Comment;
import com.example.beproject.entity.comment.CommentEntity;
import com.example.beproject.service.CommentService.CommentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository commentJPARepository;

    @Override
    public CommentEntity save(Comment comment) {
        return commentJPARepository.save(CommentEntity.from(comment));
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentJPARepository.findById(id).map(CommentEntity::toDTO);
    }

    @Override
    public CommentEntity update(CommentEntity commentEntity) throws CommentNotFoundException {
        CommentEntity updatedCommentEntity = commentJPARepository.findById(commentEntity.getId())
                .map(existingCommentEntity -> {
                    existingCommentEntity.updateFrom(commentEntity);
                    return commentJPARepository.save(existingCommentEntity);
                })
                .orElseThrow(() -> new CommentNotFoundException(commentEntity.getId()));

        return updatedCommentEntity;
    }//throws CommentNotFoundException예외처리

    @Override
    public void delete(Long id) {
        commentJPARepository.deleteById(id);
    }

    @Override
    public List<CommentEntity> findAll() {
        return commentJPARepository.findAll();
    }
}
