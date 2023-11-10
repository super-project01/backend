package com.example.beproject.repository.comment;

import com.example.beproject.domain.comment.Comment;
import com.example.beproject.domain.comment.UpdateComment;
import com.example.beproject.entity.comment.CommentEntity;
import com.example.beproject.exception.CommentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository {

    private final CommentJpaRepository commentJPARepository;

    @Override
    public Comment save(Comment comment) {
        CommentEntity commentEntity = CommentEntity.from(comment);
        CommentEntity savedEntity = commentJPARepository.save(commentEntity);
        return savedEntity.toDTO();
    }

    @Override
    public Optional<Comment> findById(Long id) {
        return commentJPARepository.findById(id).map(CommentEntity::toDTO);
    }

    @Override
    public Comment update(Long id, UpdateComment updateComment) throws CommentNotFoundException {
        CommentEntity existingCommentEntity = commentJPARepository.findById(id)
                .orElseThrow(() -> new CommentNotFoundException(id));

        if (updateComment.getContents() != null) {
            existingCommentEntity.setContents(updateComment.getContents());
            CommentEntity savedEntity = commentJPARepository.save(existingCommentEntity);
            return savedEntity.toDTO();
        } else {
            return existingCommentEntity.toDTO();
        }
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

    @Override
    public Comment getComment(Long id) throws CommentNotFoundException {
        return commentJPARepository.findById(id)
                .map(CommentEntity::toDTO)
                .orElseThrow(() -> new CommentNotFoundException(id));
    }

}