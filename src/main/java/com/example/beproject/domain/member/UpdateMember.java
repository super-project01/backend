package com.example.beproject.domain.member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateMember {

    private String email;
    private String nickname;
    private String password;

    @Builder
    public UpdateMember(String email, String nickname, String password) {
        this.email = email;
        this.nickname = nickname;
        this.password = password;
    }
}