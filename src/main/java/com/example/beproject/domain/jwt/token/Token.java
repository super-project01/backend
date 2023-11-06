package com.example.beproject.domain.jwt.token;

import com.example.beproject.entity.token.TokenEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@ToString
@NoArgsConstructor
public class Token {

    private Long id;
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private String memberEmail;
    private LocalDateTime accessTokenTime;
    private LocalDateTime refreshTokenTime;

    @Builder
    public Token(Long id,
                    String grantType,
                    String accessToken,
                    String refreshToken,
                    String memberEmail,
                    LocalDateTime accessTokenTime,
                    LocalDateTime refreshTokenTime) {
        this.id = id;
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.memberEmail = memberEmail;
        this.accessTokenTime = accessTokenTime;
        this.refreshTokenTime = refreshTokenTime;
    }

    public static Token from(TokenEntity token){
        return Token.builder()
                .id(token.getId())
                .grantType(token.getGrantType())
                .accessToken(token.getAccessToken())
                .refreshToken(token.getRefreshToken())
                .memberEmail(token.getMemberEmail())
                .accessTokenTime(token.getAccessTokenTime())
                .refreshTokenTime(token.getRefreshTokenTime())
                .build();
    }

    public void setAccessToken(String reIssueAccessToken, LocalDateTime expiredTime) {
        this.accessToken = reIssueAccessToken;
        this.accessTokenTime = expiredTime;
    }

    public void setRefreshToken(String reIssueRefreshToken, LocalDateTime expiredTime) {
        this.refreshToken = reIssueRefreshToken;
        this.refreshTokenTime = expiredTime;
    }
}
