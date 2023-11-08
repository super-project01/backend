package com.example.beproject.domain.member;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
public class CreateMember {

    @Getter
    private final String id;

    // 이메일 유효성 검사
    @NotBlank(message = "이메일은 필수 입력 항목입니다.")
    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    private String email;
    private final String password;
    private String nickname;

    @Builder
    public CreateMember(String id, String email, String password, String nickname) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }
}