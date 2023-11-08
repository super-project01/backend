package com.example.beproject.entity.member;

import com.example.beproject.domain.member.Member;
import com.example.beproject.domain.member.MemberStatus;
import com.example.beproject.domain.member.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@Table(name = "member")
@NoArgsConstructor
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

    @Enumerated(EnumType.STRING)
    @Column(name="role", nullable = false)
    private Role role;

    @Builder
    public MemberEntity(Long id, String email, String password, String nickname, MemberStatus status, Role role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.status = status;
        this.role = role;
    }

    // DTO to Entity
    public static MemberEntity from(Member member){
        return MemberEntity.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .status(member.getStatus())
                .role(member.getRole())
                .build();
    }

    // Entity to DTO
    public Member toDTO(){
        return Member.builder()
                .id(this.id)
                .email(this.email)
                .password(this.password)
                .nickname(this.nickname)
                .status(this.status)
                .role(this.role)
                .build();
    }
}
