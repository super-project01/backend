package com.example.beproject.controller.post;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "게시글", description = "게시글 API입니다.")
@Slf4j
@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    @GetMapping("/")
    @Tag(name = "post")
    @Operation(summary = "게시글", description = "게시글 전체조회 API입니다")
    public ResponseEntity<?> getAllPost(@PathVariable long id) {

        //Service return

        return ResponseEntity.ok()
                .body("Service return");
    }


}
