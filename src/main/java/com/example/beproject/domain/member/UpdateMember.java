package com.example.beproject.domain.member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateMember {

    private String id;
    private String email;
    private String nickname;

    @Builder
    public UpdateMember(String id, String email, String nickname) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
    }
}