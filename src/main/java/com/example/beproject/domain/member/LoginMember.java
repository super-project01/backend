package com.example.beproject.domain.member;

import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginMember {
    private String email;
    private String password;

    @Builder
    public LoginMember(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
