package com.example.beproject.service.member;

import com.example.beproject.domain.member.CreateMember;
import com.example.beproject.domain.member.Member;
import com.example.beproject.entity.member.MemberEntity;
import com.example.beproject.repository.member.MemberJPARepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Builder
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberJPARepository repository;
    @Override
    @Transactional
    public Member register(CreateMember member) {

        return null;
    }
}
