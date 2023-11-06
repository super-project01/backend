package com.example.beproject.domain.jwt;

import com.example.beproject.domain.jwt.token.Token;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.security.auth.Subject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtProvider {
    private static final String SUBJECT_ACCESS = "access_token";
    private static final String SUBJECT_REFRESH = "refresh_token";
    private static final String EMAIL_CLAIMS = "email";

    private static final String AUTHORITIES_KEY = "auth";
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_AUTHORIZATION_Refresh = "Authorization-refresh";

    @Value("${jwt.access.expiration}")
    private long ACCESS_TOKEN_EXPIRE_TIME;

    @Value("${jwt.refresh.expiration}")
    private long REFRESH_TOKEN_EXPIRE_TIME;

    @Value("${jwt.secret_key}")
    private String secretKey;

    private Key key;
    private final ObjectMapper objectMapper;

    @PostConstruct
    public void Init() {

        byte[] keyBytes = DatatypeConverter.parseBase64Binary(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    // <summary>
    // TOKEN DTO 사용
    // </summary>
    // 회원 토큰 최초 생성
    public Token createToken(Authentication authentication, List<GrantedAuthority> authorities) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(AUTHORITIES_KEY, authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        claims.put(EMAIL_CLAIMS, authentication.getName());

        // JWT 시간 설정
        long now = (new Date()).getTime();

        // AccessToken 생성
        Date accessTokenExpire = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);
        String accessToken = createAccessToken(claims, accessTokenExpire);

        // RefreshToken 생성
        Date refreshTokenExpire = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);
        String refreshToken = createRefreshToken(refreshTokenExpire);

        Token token = Token.builder()
                .grantType("Bearer ")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .accessTokenTime((accessTokenExpire.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()))
                .refreshTokenTime((refreshTokenExpire.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime()))
                .memberEmail(authentication.getName())
                .build();

        log.info("token in JwtProvider : " + token);
        return token;
    }

    // AccessToken 생성
    public String createAccessToken(Map<String, Object> claims, Date expiredTime) {
        long now = new Date().getTime();
        return Jwts.builder()
                .setSubject(SUBJECT_ACCESS)
                .setClaims(claims)
                .setExpiration(expiredTime)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // RefreshToken 생성
    public String createRefreshToken(Date expiredTime) {
        long now = new Date().getTime();
        return Jwts.builder()
                .setSubject(SUBJECT_REFRESH)
                .setExpiration(expiredTime)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    // <summary>
    // TOKEN DTO 사용 끝
    // </summary>

    public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
        response.setHeader(HEADER_AUTHORIZATION, accessToken);
    }

    public void setRefreshTokenHeader(HttpServletResponse response, String refreshToken) {
        response.setHeader(HEADER_AUTHORIZATION_Refresh, refreshToken);
    }

    public void sendAccessToken(HttpServletResponse response, String accessToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        response.setHeader(HEADER_AUTHORIZATION, accessToken);
        log.info("재발급된 Access Token : {}", accessToken);
    }

    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        setAccessTokenHeader(response, accessToken);
        setRefreshTokenHeader(response, refreshToken);
        log.info("Access Token, Refresh Token 헤더 설정 완료");
    }

    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HEADER_AUTHORIZATION_Refresh))
                .filter(refreshToken -> refreshToken.startsWith("Bearer "))
                .map(refreshToken -> refreshToken.replace("Bearer ", ""));
    }

    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(HEADER_AUTHORIZATION))
                .filter(refreshToken -> refreshToken.startsWith("Bearer "))
                .map(refreshToken -> refreshToken.replace("Bearer ", ""));
    }

    // RefreshToken으로 AccessToken생성
    public Token reIssueAccessToken(String userEmail, List<GrantedAuthority> authorities) {
        Long now = (new Date()).getTime();
        Date accessTokenExpire = new Date(now + ACCESS_TOKEN_EXPIRE_TIME);

        log.info("authorities : " + authorities);

        Map<String, Object> claims = new HashMap<>();
        claims.put(AUTHORITIES_KEY, authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        claims.put(EMAIL_CLAIMS, userEmail);

        String accessToken = createAccessToken(claims, accessTokenExpire);

        Token token = Token.builder()
                .grantType("Bearer ")
                .accessToken(accessToken)
                .memberEmail(userEmail)
                .accessTokenTime(accessTokenExpire.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .build();

        return token;
    }

    //refreshToken재발급
    public String reIssueRefreshToken(Date expiredTime){

        return createRefreshToken(expiredTime);
    }
/*
    // JWT 토큰을 복호화하여 토큰에 들어있는 정보를 꺼내는 코드
    // 토큰으로 클레임을 만들고 이를 이용해 유저 객체를 만들어서 최종적으로 authentication 객체를 리턴
    public Authentication getAuthentication(String token) {
        // 토큰 복호화 메소드
        Claims claims = parseClaims(token);
        log.info("claims : " + claims);

        if(claims.get("auth") == null) {
            log.info("권한 정보가 없는 토큰입니다.");
        }
        // 권한 정보 가져오기
        List<String> authority = (List<String>) claims.get(AUTHORITIES_KEY);
        log.info("authority : " + authority);

        Collection<? extends GrantedAuthority> authorities =
                authority.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        UserDetails userDetails = new User(claims.getSubject(), "", authorities);
        log.info("subject : " + claims.getSubject());

        // Spring Security에서 인증을 나타내는 객체로 사용됩니다.
        return new UsernamePasswordAuthenticationToken(userDetails, token, authorities);
    }

    // 토큰 복호화 메소드
    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            log.info("ExpiredJwtException : " + e.getMessage());
            log.info("ExpiredJwtException : " + e.getClaims());
            return e.getClaims();
        }
    }
*/
    // 토큰 검증하기
    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (ExpiredJwtException e) {
            log.error("만료된 토큰입니다.");
            return false;
        } catch (SecurityException | MalformedJwtException
                 | IllegalArgumentException | UnsupportedJwtException e) {
            log.error("올바르지 않은 토큰입니다.");
            return false;
        }
    }
}
