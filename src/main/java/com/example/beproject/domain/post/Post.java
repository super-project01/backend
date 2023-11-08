package com.example.beproject.domain.post;

import com.example.beproject.entity.post.PostEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Column;

@Getter
public class Post {

    private final Long id;
    private final String subject;
    private final String detail;
    private final Long writer;
    private final String tag;
    private final PostStatus status;

    @Builder
    public Post(Long id, String subject, String detail, Long writer, String tag, PostStatus status) {
        this.id = id;
        this.subject = subject;
        this.detail = detail;
        this.writer = writer;
        this.tag = tag;
        this.status = status;
    }

    //Entity to DTO
    public static Post from(PostEntity post){
        return Post.builder()
                .id(post.getId())
                .subject(post.getSubject())
                .detail(post.getDetail())
                .writer(post.getWriter())
                .tag(post.getTag())
                .status(post.getStatus())
                .build();
    }

}
