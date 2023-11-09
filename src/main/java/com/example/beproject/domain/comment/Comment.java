package com.example.beproject.domain.comment;

import com.example.beproject.domain.post.Post;
import com.example.beproject.entity.comment.CommentEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Comment {

    private final Long id;

    private final Long write;

    private final String contents;

    private final Long orgid;

    private final Long subid;

    private final CommentStatus status;

    private final Post post;


    @Builder
    public Comment(Long id, Long write, String contents, Long orgid, Long subid, CommentStatus status, Post post) {
        this.id = id;
        this.write = write;
        this.contents = contents;
        this.orgid = orgid;
        this.subid = subid;
        this.status = status;
        this.post = post;
    }

    public static Comment from(CommentEntity comment) {
        return Comment.builder()
                .id(comment.getId())
                .write(comment.getWrite())
                .contents(comment.getContents())
                .orgid(comment.getOrgid())
                .subid(comment.getSubid())
                .status(comment.getStatus())
                .post(Post.from(comment.getPost()))
                .build();
    }// CommentEntity를 Comment로
}