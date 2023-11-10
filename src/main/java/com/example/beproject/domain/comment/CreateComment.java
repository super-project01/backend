package com.example.beproject.domain.comment;

import com.example.beproject.domain.post.Post;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateComment {
    private Long write;
    private String contents;
    private Long postId;

    @Builder
    public CreateComment(Long write, String contents, Post post) {
        this.write = write;
        this.contents = contents;
        this.postId = postId;
    }
}