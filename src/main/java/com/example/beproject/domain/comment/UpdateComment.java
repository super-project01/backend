package com.example.beproject.domain.comment;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateComment {

    private String contents;

    @Builder
    public UpdateComment(String contents) {
        this.contents = contents;
    }
}