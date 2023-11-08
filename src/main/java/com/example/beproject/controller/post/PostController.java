package com.example.beproject.controller.post;

import com.example.beproject.domain.member.CreateMember;
import com.example.beproject.domain.post.CreatePost;
import com.example.beproject.domain.post.Post;
import com.example.beproject.domain.post.UpdatePost;
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
    @Operation(summary = "게시글", description = "게시글 등록하는 API입니다")

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


//    @PostMapping("/")
//    @Tag(name = "POST")
//    @Operation(summary = "게시글", description = "게시글 수정하는 API입니다")
//
//    public ResponseEntity<?> save(@RequestBody UpdatePost updatePost,
//                                  BindingResult result) {
//        try {
//            if (result.hasErrors()) {
//                log.info("BindingResult error : " + result.hasErrors());
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getClass().getSimpleName());
//                postService.updatePost(updatePost);
//            }
//            return ResponseEntity.status(HttpStatus.CREATED).body(postService.updatePost(updatePost));
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
//        }
//    }

}
