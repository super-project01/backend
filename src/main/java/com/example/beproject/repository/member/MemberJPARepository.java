package com.example.beproject.repository.member;

import com.example.beproject.entity.member.MemberEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface MemberJPARepository extends JpaRepository<MemberEntity, Long> {

    public MemberEntity findByEmail(String email);
}
