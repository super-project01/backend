package com.example.beproject.controller.comment;

import com.example.beproject.domain.comment.Comment;
import com.example.beproject.domain.comment.CommentStatus;
import com.example.beproject.domain.comment.CreateComment;
import com.example.beproject.domain.comment.UpdateComment;
import com.example.beproject.domain.post.Post;
import com.example.beproject.exception.CommentNotFoundException;
import com.example.beproject.repository.post.PostRepository;
import com.example.beproject.service.CommentService.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("/comment/{id}")
    @Tag(name = "COMMENT")
    @Operation(summary = "댓글 조회", description = "댓글 조회")
    public ResponseEntity<Comment> getComment(@PathVariable Long id) throws CommentNotFoundException {
        Comment comment = commentService.getComment(id);
        return ResponseEntity.ok(comment);
    }

    @PostMapping("/")
    public <CommentRequest> ResponseEntity<String> createComment(@RequestBody CommentRequest commentRequest) {
        try {
            return new ResponseEntity<>("댓글이 성공적으로 생성되었습니다.", HttpStatus.OK);
        } catch (CommentNotFoundException | CommentNotFoundException.PostNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("댓글 생성 중에 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("{id}")
    @Tag(name = "COMMENT")
    @Operation(summary = "댓글 수정", description = "댓글 수정")
    public ResponseEntity<Comment> updateComment(@PathVariable Long id, @RequestBody UpdateComment comment) {
        try {
            Comment updatedComment = commentService.updateComment(id, comment);
            return ResponseEntity.ok(updatedComment);
        } catch (CommentNotFoundException.PostNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/comment/{id}")
    @Tag(name = "COMMENT")
    @Operation(summary = "댓글 삭제", description = "댓글 삭제")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.noContent().build();
    }
}
