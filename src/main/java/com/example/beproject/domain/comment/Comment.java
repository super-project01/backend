package com.example.beproject.domain.comment;

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


    @Builder
    public Comment(Long id, Long write, String contents, Long orgid, Long subid, String status) {
        this.id = id;
        this.write = write;
        this.contents = contents;
        this.orgid = orgid;
        this.subid = subid;
        this.status = status;

    }

}
