package com.example.beproject.domain.post;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponsePost {

    private final Long id;
    private final String subject;
    private final String detail;
    private final Long writer;
    private final String tag;

    @Builder
    public ResponsePost(Long id, String subject, String detail, Long writer, String tag) {
        this.id = id;
        this.subject = subject;
        this.detail = detail;
        this.writer = writer;
        this.tag = tag;
    }

    public static ResponsePost of(Post post){
        return ResponsePost.builder()
                .id(post.getId())
                .subject(post.getSubject())
                .detail(post.getDetail())
                .writer(post.getWriter())
                .tag(post.getTag())
                .build();
    }
}
