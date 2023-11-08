package com.example.beproject.service.member;

import com.example.beproject.controller.member.Response.ResponseMember;
import com.example.beproject.domain.jwt.token.Token;
import com.example.beproject.domain.member.CreateMember;
import com.example.beproject.domain.member.LoginMember;
import com.example.beproject.domain.member.Member;

import javax.servlet.http.HttpServletRequest;

public interface MemberService {

    String encodePassword(String password);
    boolean matchesPassword(String rawPassword, String encodedPassword);

    Member register(CreateMember member);

    Token login(String email, String pw);

    public String logout(HttpServletRequest request, String email);

    ResponseMember getByEmail(String email);
}
