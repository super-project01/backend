package com.example.beproject.controller.comment;

import com.example.beproject.domain.comment.Comment;
import com.example.beproject.domain.comment.CreateComment;
import com.example.beproject.domain.comment.UpdateComment;
import com.example.beproject.exception.CommentException;
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

    @GetMapping("/")
    @Tag(name = "COMMENT")
    @Operation(summary = "댓글", description = "모든 댓글 조회")
    public ResponseEntity<List<Comment>> getAllComments() {
        List<Comment> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
        /** public List<Comment> getAllComments() {
         return commentService.getAllComments(); 로도 사용가능
         }*/
    }

    @GetMapping("/comment/{id}")
    @Tag(name = "COMMENT")
    @Operation(summary = "댓글 조회", description = "댓글 조회")
    public ResponseEntity<Comment> getComment(@PathVariable Long id) throws CommentException.CommentNotFoundException {
        Comment comment = commentService.getComment(id);
        return ResponseEntity.ok(comment);
    }

    @PostMapping("/")
    @Tag(name = "COMMENT")
    @Operation(summary = "댓글 추가", description = "댓글 추가")
    public ResponseEntity<Comment> createComment(@RequestBody CreateComment comment) {
        Comment createdComment = commentService.createComment(comment);
        return ResponseEntity.ok(createdComment);
    }

    @PutMapping("/comment/{id}")
    @Tag(name = "COMMENT")
    @Operation(summary = "댓글 수정", description = "댓글 수정")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody UpdateComment comment) throws CommentException.CommentNotFoundException {
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