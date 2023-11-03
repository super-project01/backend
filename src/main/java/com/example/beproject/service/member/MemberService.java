package com.example.beproject.service.member;

import com.example.beproject.domain.member.CreateMember;
import com.example.beproject.domain.member.Member;

public interface MemberService {

    Member register(CreateMember member);
}
