package com.example.beproject.service.member;

import com.example.beproject.controller.member.Response.ResponseMember;
import com.example.beproject.domain.jwt.token.Token;
import com.example.beproject.domain.member.CreateMember;
import com.example.beproject.domain.member.LoginMember;
import com.example.beproject.domain.member.Member;

public interface MemberService {

    Member register(CreateMember member);

    Token login(String email, String pw);

    ResponseMember getByEmail(String email);
}
