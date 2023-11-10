package com.example.beproject.service.CommentService;

import com.example.beproject.domain.comment.Comment;
import com.example.beproject.domain.comment.CommentStatus;
import com.example.beproject.domain.comment.CreateComment;
import com.example.beproject.domain.comment.UpdateComment;
import com.example.beproject.domain.post.Post;
import com.example.beproject.entity.comment.CommentEntity;
import com.example.beproject.exception.CommentNotFoundException;
import com.example.beproject.repository.comment.CommentRepository;
import com.example.beproject.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    public Comment createComment(CreateComment createComment) {
        Long postId = createComment.getPostId();
        Post post = postRepository.getPost(postId); //오류 getPost, postRepository에서 추가해야하는 부분? comment에서 고치거나 뺄 수 있는지
        if (post == null) {
            throw new CommentNotFoundException(postId);
        }
        Comment newComment = Comment.builder()
                .write(createComment.getWrite())
                .contents(createComment.getContents())
                .status(CommentStatus.NEW)
                .post(post)
                .build();
        Comment savedComment = commentRepository.save(newComment);
        return savedComment;
    }

    @Override
    public List<Comment> getAllComments() {
        List<Comment> comment = commentRepository.findAll();
        return comment;
    }

    @Override
    public Comment getComment(Long id) throws CommentNotFoundException {
        Comment comment = commentRepository.getComment(id);
        if (comment == null) {
            throw new CommentNotFoundException(id);
        }
        return comment;
    }

    @Override
    public Comment updateComment(Long id, UpdateComment updateComment) {
        try {
            CommentEntity existingCommentEntity = CommentEntity.from(commentRepository.findById(id)
                    .orElseThrow(() -> new CommentNotFoundException.PostNotFoundException("댓글을 찾을 수 없습니다. " + id)));

            if (updateComment.getContents() != null) {
                CommentEntity updatedEntity = CommentEntity.builder()
                        .id(existingCommentEntity.getId())
                        .write(existingCommentEntity.getWrite())
                        .contents(updateComment.getContents())
                        .orgid(existingCommentEntity.getOrgid())
                        .subid(existingCommentEntity.getSubid())
                        .status(existingCommentEntity.getStatus())
                        .post(existingCommentEntity.getPost())
                        .build();

                CommentEntity savedEntity = CommentEntity.from(commentRepository.save(Comment.from(updatedEntity)));
                return savedEntity.toDTO();
            } else {
                return existingCommentEntity.toDTO();
            }
        } catch (CommentNotFoundException.PostNotFoundException ex) {
            throw new CommentNotFoundException(id);
        }
    }


    @Override
    public void deleteComment(Long id) {
        commentRepository.delete(id);
    }
}