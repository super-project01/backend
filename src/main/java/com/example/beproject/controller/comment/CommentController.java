package com.example.beproject.controller.comment;

import com.example.beproject.domain.comment.Comment;
import com.example.beproject.domain.comment.CreateComment;
import com.example.beproject.domain.comment.UpdateComment;
import com.example.beproject.exception.CommentNotFoundException;
import com.example.beproject.repository.post.PostRepository;
import com.example.beproject.service.CommentService.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
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

    @GetMapping("{id}")
    @Tag(name = "COMMENT")
    @Operation(summary = "댓글 조회", description = "댓글 조회")
    public ResponseEntity<?> getComment(@PathVariable Long id) {
        try {
            Comment comment = commentService.getComment(id);
            return ResponseEntity.ok(comment);
        } catch (CommentNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("댓글을 찾을 수 없습니다");
        }
    }

    @PostMapping("/")
    @Tag(name = "COMMENT")
    @Operation(summary = "댓글 생성", description = "댓글 생성")
    public ResponseEntity<?> createComment(@RequestBody CreateComment createComment) {
        try {
            Comment comment = commentService.createComment(createComment);
            return ResponseEntity.ok(comment);
        } catch (CommentNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }


    @PutMapping("{id}")
    @Tag(name = "COMMENT")
    @Operation(summary = "댓글 수정", description = "댓글 수정")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody UpdateComment comment) {
        try {
            Comment updatedComment = commentService.updateComment(id, comment);
            return ResponseEntity.ok(updatedComment);
        } catch (CommentNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        try {
            commentService.deleteComment(id);
            return new ResponseEntity<>("삭제 성공", HttpStatus.OK);
        } catch (EmptyResultDataAccessException e) {
            return new ResponseEntity<>("댓글을 찾을 수 없습니다", HttpStatus.NOT_FOUND);
        }
    }
}