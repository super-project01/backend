package com.example.beproject.repository.member;

import com.example.beproject.domain.member.Member;
import com.example.beproject.entity.member.MemberEntity;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member);

    public List<Member> findAll();

    public Optional<Member> findById(long id);
}
