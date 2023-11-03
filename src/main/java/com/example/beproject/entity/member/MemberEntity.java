package com.example.beproject.entity.member;

import com.example.beproject.domain.member.Member;
import com.example.beproject.domain.member.MemberStatus;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "member")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_email")
    private String email;

    @Column(name="member_pw")
    private String password;

    @Column(name = "member_nick")
    private String nickname;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private MemberStatus status;

    @Builder
    public MemberEntity(Long id, String email, String password, String nickname, MemberStatus status) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.status = status;
    }

    public static MemberEntity from(Member member){
        return MemberEntity.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .status(member.getStatus())
                .build();
    }

    public Member toDTO(){
        return Member.builder()
                .id(this.id)
                .email(this.email)
                .password(this.password)
                .nickname(this.nickname)
                .status(this.status)
                .build();
    }
}
