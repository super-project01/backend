package com.example.beproject.domain.post;

import com.example.beproject.entity.post.PostEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Post {

    private final Long id;
    private String subject;
    private String detail;
    private final Long writer;
    private String tag;
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

    public void updatePost(String subject, String detail, String tag) {
        if(!subject.isEmpty())
            this.subject = subject;
        if(!detail.isEmpty())
            this.detail = detail;
        if(!tag.isEmpty())
            this.tag = tag;
    }


}
