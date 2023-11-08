package com.example.beproject.controller.member.Response;

import com.example.beproject.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseMember {
    private final Long id;
    private final String email;
    private final String nickname;

    @Builder
    public ResponseMember(Long id, String email, String nickname) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
    }

    public static ResponseMember of(Member member){
        return ResponseMember.builder()
                .id(member.getId())
                .email(member.getEmail())
                .nickname(member.getNickname())
                .build();
    }
}
