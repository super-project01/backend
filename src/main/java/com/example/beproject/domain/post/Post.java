package com.example.beproject.domain.post;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;

@Getter
public class Post {

    private Long id;
    private String subject;
    private String detail;
    private Long writer;
    private String tag;

    @Builder
    public Post(Long id, String subject, String detail, Long writer, String tag) {
        this.id = id;
        this.subject = subject;
        this.detail = detail;
        this.writer = writer;
        this.tag = tag;
    }
}
