package com.example.beproject.repository.token;

import com.example.beproject.domain.jwt.token.Token;
import com.example.beproject.entity.token.TokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class TokenRepositoryImpl implements TokenRepository{

    private final TokenJpaRepository tokenJpaRepository;

    @Override
    public Token save(Token token) {
        return Token.from(tokenJpaRepository.save(TokenEntity.from(token)));
    }

    @Override
    public Token findByRefreshToken(String refreshToken) {
        return Token.from(tokenJpaRepository.findByRefreshToken(refreshToken));
    }

    @Override
    public Token findByMemberEmail(String email) {
        if(tokenJpaRepository.findByMemberEmail(email).isPresent())
            return Token.from(tokenJpaRepository.findByMemberEmail(email).get());

        return null;
    }

    @Override
    public Token findByAccessToken(String accessToken) {
        return Token.from(tokenJpaRepository.findByAccessToken(accessToken));
    }
}
