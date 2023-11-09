package com.example.beproject.controller.comment;

import com.example.beproject.domain.comment.Comment;
import com.example.beproject.domain.comment.CommentStatus;
import com.example.beproject.domain.comment.CreateComment;
import com.example.beproject.domain.comment.UpdateComment;
import com.example.beproject.domain.post.Post;
import com.example.beproject.repository.post.PostRepository;
import com.example.beproject.exception.CommentNotFoundException;
import com.example.beproject.service.CommentService.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
@Tag(name = "COMMENT", description = "댓글")
public class CommentController {

    private final CommentService commentService;
    private final PostRepository postRepository;

    @GetMapping("/")
    @Tag(name = "COMMENT")
    @Operation(summary = "댓글", description = "모든 댓글 조회")
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/comment/{id}")
    @Tag(name = "COMMENT")
    @Operation(summary = "댓글 조회", description = "댓글 조회")
    public ResponseEntity<Comment> getComment(@PathVariable Long id) throws CommentNotFoundException {
        Comment comment = commentService.getComment(id);
        return ResponseEntity.ok(comment);
    }

    @PostMapping("/")
    @Tag(name = "COMMENT")
    @Operation(summary = "댓글 추가", description = "댓글 추가")
    public ResponseEntity<Comment> createComment(@RequestBody CreateComment comment) throws CommentNotFoundException.PostNotFoundException {
        Long postId = comment.getPostId();
        Post post = postRepository.getPost(postId); //오류 getPost
        if (post == null) {
            throw new CommentNotFoundException.PostNotFoundException(postId);
        }
        Comment newComment = Comment.builder()
                .write(comment.getWrite())
                .contents(comment.getContents())
                .status(CommentStatus.NEW)
                .post(post)
                .build();
        Comment createdComment = commentService.createComment(comment);
        return ResponseEntity.ok(createdComment);
    }

    @PutMapping("/comment/{id}")
    @Tag(name = "COMMENT")
    @Operation(summary = "댓글 수정", description = "댓글 수정")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody UpdateComment comment) throws CommentNotFoundException {
        Comment updatedComment = commentService.updateComment(id, comment);
        return ResponseEntity.ok(updatedComment);
    }

    @DeleteMapping("/comment/{id}")
    @Tag(name = "COMMENT")
    @Operation(summary = "댓글 삭제", description = "댓글 삭제")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
