package com.example.beproject.service.CommentService;

import com.example.beproject.domain.comment.Comment;
import com.example.beproject.domain.comment.CommentStatus;
import com.example.beproject.domain.comment.CreateComment;
import com.example.beproject.domain.comment.UpdateComment;
import com.example.beproject.domain.post.Post;
import com.example.beproject.entity.comment.CommentEntity;
import com.example.beproject.exception.CommentException;
import com.example.beproject.repository.comment.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override //@RequestBody 이렇게 사용하는거 맞는지 물어보기
    public Comment createComment(@RequestBody CreateComment create) {
        Comment newComment = Comment.builder()
                .write(create.getWrite())
                .contents(create.getContents())
                .status(CommentStatus.NEW)
                .post(Post.builder().id(create.getPostId()).build())
                .build();
        Comment savedComment = commentRepository.save(newComment);
        return savedComment;
    }

    @Override
    public List<Comment> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments;
    }

    @Override
    public Comment getComment(Long id) throws CommentException.CommentNotFoundException {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentException.CommentNotFoundException(id));
        return comment;
    }

    @Override
    public Comment updateComment(Long id, @RequestBody UpdateComment updateComment) throws CommentException.CommentNotFoundException {
        Comment existingComment = commentRepository.findById(id)
                .orElseThrow(() -> new CommentException.CommentNotFoundException(id));
        Comment updatedComment = commentRepository.update(existingComment);
        return updatedComment;
    }//setContents(String) Comment 객체가 contents 필드를 수정 및 업데이트 해야하는 경우에만 추가하면 되는데 해야하는지 물어보기.

    @Override
    public void deleteComment(Long id) {
        commentRepository.delete(id);
    }
}