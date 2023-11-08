package com.example.beproject.domain.post;

import com.example.beproject.entity.post.PostEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreatePost {


    private final String subject;
    private final String detail;
    private final Long writer;
    private final String tag;

    @Builder
    public CreatePost(String subject, String detail, Long writer, String tag) {
        this.subject = subject;
        this.detail = detail;
        this.writer = writer;
        this.tag = tag;
    }
}
