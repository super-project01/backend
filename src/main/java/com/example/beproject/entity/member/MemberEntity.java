package com.example.beproject.entity.member;

import com.example.beproject.domain.member.MemberStatus;
import lombok.Builder;

import javax.persistence.*;

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
}
