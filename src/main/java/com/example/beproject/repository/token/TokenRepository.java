package com.example.beproject.repository.token;

import com.example.beproject.domain.jwt.token.Token;

public interface TokenRepository {

    Token save(Token token);
    Token findByRefreshToken(String refreshToken);
    Token findByMemberEmail(String memberEmail);

    Token findByAccessToken(String accessToken);
}
