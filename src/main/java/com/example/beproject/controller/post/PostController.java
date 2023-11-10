package com.example.beproject.controller.post;
import com.example.beproject.domain.post.ResponsePost;
import com.example.beproject.domain.post.CreatePost;
import com.example.beproject.domain.post.Post;
import com.example.beproject.domain.post.UpdatePost;
import com.example.beproject.exception.PostNotFoundException;
import com.example.beproject.service.post.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/post")
@Tag(name = "POST", description = "게시글")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/")
    @Tag(name = "POST")
    @Operation(summary = "게시글", description = "게시글 전체조회 API입니다")
    public ResponseEntity<?> getAllPost(@PathVariable long id) {

        //Service return

        return ResponseEntity.ok()
                .body("Service return");
    }


    @PostMapping("/")
    @Tag(name = "POST")
    @Operation(summary = "게시글 등록", description = "게시글 등록 API")

    public ResponseEntity<?> save(@RequestBody CreatePost post,
                                  BindingResult result) {
        try {
            if (result.hasErrors()) {
                log.info("BindingResult error : " + result.hasErrors());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getClass().getSimpleName());

            }
            return ResponseEntity.status(HttpStatus.CREATED).body(postService.createPost(post));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    //게시글 수정
    @PutMapping("/{id}")
    @Tag(name = "POST")
    @Operation(summary = "게시글 수정", description = "게시글 수정 API")
    public ResponseEntity<?> updatePost(@PathVariable Long id,  @RequestBody UpdatePost updatePost, BindingResult result) {
        if (result.hasErrors()) {
            log.info("BindingResult error: " + result.getAllErrors());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());
        }

        try {
            Post updatedMember = postService.updatePost(id, updatePost);

            return ResponseEntity.ok().body(ResponsePost.of(updatedMember));
        } catch (PostNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @Tag(name = "POST")
    @Operation(summary = "게시글 삭제", description = "게시글 삭제 API")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable Long id) {
        try {
            postService.deletePost(id);
            return new ResponseEntity<>("게시물이 성공적으로 삭제되었습니다.", HttpStatus.OK);
        } catch (PostNotFoundException e) {
            return new ResponseEntity<>("게시물을 찾을 수 없습니다.", HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("게시물 삭제 중 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
