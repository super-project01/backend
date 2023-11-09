package com.example.beproject.domain.comment;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateComment {
    private Long write;
    private String contents;
    private Long postId;

    @Builder
    public CreateComment(Long write, String contents, Long postId) {
        this.write = write;
        this.contents = contents;
        this.postId = postId;
    }

    public Long getOrgid() {
        Long orgid = null;
        return orgid;   }

    public Long getSubid() {  //추가하는게 맞나? 서비스에서 빼버리면 null로 자동할당.
        Long subid = null;
        return subid;    }
}
