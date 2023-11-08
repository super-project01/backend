package com.example.beproject.controller.comment;

import com.example.beproject.domain.comment.Comment;
import com.example.beproject.service.CommentService.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;
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
    public ResponseEntity<List<Comment>> getComment() {
        List<Comment> comments = commentService.getAllComment();
        return ResponseEntity.ok(comments);
    /**public List<Comment> getAllComments() {
     return commentService.getAllComments(); 로도 사용가능
     }*/
    }

    @GetMapping("/")
    @Tag(name = "COMMENT")
    @Operation(summary = "모든 댓글 조회", description = "모든 댓글 조회")
    public ResponseEntity<List<Comment>> getAllComment() {
        List<Comment> comments = commentService.getAllComment();
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/")
    @Tag(name = "COMMENT")
    @Operation(summary = "댓글 추가", description = "댓글 추가")
    public ResponseEntity<Comment> createComment(@RequestBody Comment comment) {
        Comment createdComment = Comment.from(commentService.createComment(comment));
        return ResponseEntity.ok(createdComment);
    }

    @PutMapping("/{id}")
    @Tag(name = "COMMENT")
    @Operation(summary = "댓글 수정", description = "댓글 수정")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody Comment comment) {
        Optional<Comment> updatedComment = commentService.updateComment(id, comment);
        return updatedComment
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Tag(name = "COMMENT")
    @Operation(summary = "댓글 삭제", description = "댓글 삭제")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}