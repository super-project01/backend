package com.example.beproject.domain.member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UpdateMember {

    private String nickname;
    private String password;

    @Builder
    public UpdateMember(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }

    public void setPassword(String pw){
        this.password = pw;
    }
}