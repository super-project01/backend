package com.example.beproject.domain.comment;

import com.example.beproject.domain.member.Member;
import com.example.beproject.entity.comment.CommentEntity;
import com.example.beproject.entity.member.MemberEntity;
import com.example.beproject.entity.post.PostEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Comment {

    private final Long id;
    //대댓글 아이디
    private final Long write;

    private final String contents;

    private final Long orgid;

    private final Long subid;

    private final String status;

    private final PostEntity post;


    @Builder
    public Comment(Long id, Long write, String contents, Long orgid, Long subid, String status, PostEntity post) {
        this.id = id;
        this.write = write;
        this.contents = contents;
        this.orgid = orgid;
        this.subid = subid;
        this.status = status;
        this.post  = post;
    }

    public static Comment from(CommentEntity comment) {
        return Comment.builder()
                .id(comment.getId())
                .write(comment.getWrite())
                .contents(comment.getContents())
                .orgid(comment.getOrgid())
                .subid(comment.getSubid())
                .status(comment.getStatus())
                .post(comment.getPost())
                .build();
    }// CommentEntity를 Comment로
}
