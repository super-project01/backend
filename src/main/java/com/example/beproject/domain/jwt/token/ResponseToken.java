package com.example.beproject.domain.jwt.token;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Getter

@NoArgsConstructor
public class ResponseToken {
    private String mbrEmail;
    private String accessToken;
    private String refreshToken;
    private LocalDateTime accessTokenTime;

    @Builder
    public ResponseToken(String mbrEmail, String accessToken, String refreshToken, LocalDateTime accessTokenTime) {
        this.mbrEmail = mbrEmail;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenTime = accessTokenTime;
    }

    public static ResponseToken of(Token token){
        return ResponseToken.builder()
                .mbrEmail(token.getMemberEmail())
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .accessTokenTime(token.getAccessTokenTime())
                .build();
    }
}
