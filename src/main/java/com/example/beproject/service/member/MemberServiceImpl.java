package com.example.beproject.service.member;

import com.example.beproject.domain.member.CreateMember;
import com.example.beproject.domain.member.Member;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Builder
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    @Override
    public Member register(CreateMember member) {

        return null;
    }
}
