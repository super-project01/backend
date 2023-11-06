package com.example.beproject.repository.member;

import com.example.beproject.entity.member.MemberEntity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJPARepository extends JpaRepository<MemberEntity, Long> {

    public MemberEntity findByEmail(String email);
}
