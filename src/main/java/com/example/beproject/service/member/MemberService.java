package com.example.beproject.service.member;

import com.example.beproject.controller.member.Response.ResponseMember;
import com.example.beproject.domain.jwt.token.Token;
import com.example.beproject.domain.member.CreateMember;
import com.example.beproject.domain.member.Member;
import com.example.beproject.domain.member.UpdateMember;

import javax.servlet.http.HttpServletRequest;

public interface MemberService {

    String encodePassword(String password);

    boolean matchesPassword(String rawPassword, String encodedPassword);

    Member updateMember(Long id, UpdateMember updateMember);

    Member register(CreateMember member);

    Token login(String email, String pw) throws Exception;

    public String logout(HttpServletRequest request, String email);

    ResponseMember getByEmail(String email);
}
