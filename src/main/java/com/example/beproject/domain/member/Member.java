package com.example.beproject.domain.member;

import com.example.beproject.entity.member.MemberEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Member {

    private final Long id;
    private final String email;
    private final String password;
    private final String nickname;
    private final MemberStatus status;

    @Builder
    public Member(Long id, String email, String password, String nickname, MemberStatus status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.status = status;
    }

    //Entity to DTO
    public static Member from(MemberEntity member){
        return Member.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .status(member.getStatus())
                .build();
    }

}
