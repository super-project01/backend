package com.example.beproject.repository.comment;

import com.example.beproject.domain.comment.Comment;
import com.example.beproject.entity.comment.CommentEntity;
import com.example.beproject.exception.CommentException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository commentJPARepository;

    @Override
    public Comment save(@RequestBody Comment comment) {
        CommentEntity commentEntity = CommentEntity.from(comment);
        CommentEntity savedEntity = commentJPARepository.save(commentEntity);
        return savedEntity.toDTO();
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentJPARepository.findById(id).map(CommentEntity::toDTO);
    }

    @Override
    public Comment update(@RequestBody Comment comment) throws CommentException.CommentNotFoundException {
        CommentEntity updatedEntity = commentJPARepository.findById(comment.getId())
                .map(existingCommentEntity -> {
                    existingCommentEntity.toDTO();
                    return commentJPARepository.save(existingCommentEntity);
                })
                .orElseThrow(() -> new CommentException.CommentNotFoundException(comment.getId()));
        return updatedEntity.toDTO();
    }

    @Override
    public void delete(Long id) {
        commentJPARepository.deleteById(id);
    }

    @Override
    public List<Comment> findAll() {
        List<CommentEntity> commentEntities = commentJPARepository.findAll();
        return commentEntities.stream()
                .map(CommentEntity::toDTO)
                .collect(Collectors.toList());
    }
}