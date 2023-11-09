package com.example.beproject.domain.post;

import com.example.beproject.entity.post.PostEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdatePost {


    private final String subject;
    private final String detail;
    private final String tag;
    private final PostStatus status;


    @Builder
    public UpdatePost(String subject, String detail, String tag, PostStatus status) {
        this.subject = subject;
        this.detail = detail;
        this.tag = tag;
        this.status = status;
    }
}
