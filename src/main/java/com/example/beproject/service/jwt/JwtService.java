package com.example.beproject.service.jwt;

import com.example.beproject.domain.jwt.token.Token;
import com.example.beproject.domain.member.Member;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;

public interface JwtService {

    void createAccessTokenHeader(HttpServletResponse response, String refreshToken);

    Member checkAccessTokenValid(String accessToken);
}
