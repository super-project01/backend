package com.example.beproject.domain.jwt.token;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private Date accessTokenTime;
    private Date refreshTokenTime;

    @Builder
    public Token(Long id,
                    String grantType,
                    String accessToken,
                    String refreshToken,
                    String memberEmail,
                    Date accessTokenTime,
                    Date refreshTokenTime) {
        this.id = id;
        this.grantType = grantType;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.memberEmail = memberEmail;
        this.accessTokenTime = accessTokenTime;
        this.refreshTokenTime = refreshTokenTime;
    }

}
