package com.example.beproject.service.jwt;

import com.example.beproject.domain.jwt.JwtAuthenticationFilter;
import com.example.beproject.domain.jwt.JwtProvider;
import com.example.beproject.domain.jwt.token.Token;
import com.example.beproject.domain.member.Member;
import com.example.beproject.domain.member.Role;
import com.example.beproject.exception.MemberNotFoundException;
import com.example.beproject.repository.member.MemberRepository;
import com.example.beproject.repository.token.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class JwtServiceImpl implements JwtService{

    private final TokenRepository tokenRepository;
    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;

    @Value("${jwt.refresh.expiration}")
    private long REFRESH_TOKEN_EXPIRE_TIME;

    @Override
    public void createAccessTokenHeader(HttpServletResponse response, String refreshToken) {
        if(jwtProvider.validateToken(refreshToken)) {
            Token byRefreshToken = tokenRepository.findByRefreshToken(refreshToken);
            // 아이디 추출
            String userEmail = byRefreshToken.getMemberEmail();
            log.info("userEmail : " + userEmail);

            Member byUserEmail = memberRepository.findByEmail(userEmail);
            log.info("member : " + byUserEmail);

            List<GrantedAuthority> authorities = getAuthoritiesForUser(byUserEmail);

            Token newToken = jwtProvider.reIssueAccessToken(userEmail, authorities);
            byRefreshToken.setAccessToken(newToken.getAccessToken(), newToken.getAccessTokenTime());

            HttpHeaders headers = new HttpHeaders();

            //refreshToken 만료 하루 전 - 재발행
            if(byRefreshToken.getRefreshTokenTime().isBefore(LocalDateTime.now().minusDays(1))){
                long now = (new Date()).getTime();
                Date refreshTokenExpire = new Date(now + REFRESH_TOKEN_EXPIRE_TIME);
                refreshToken = jwtProvider.reIssueRefreshToken(refreshTokenExpire);

                byRefreshToken.setRefreshToken(refreshToken, refreshTokenExpire.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
                tokenRepository.save(byRefreshToken);
                jwtProvider.sendAccessAndRefreshToken(response, byRefreshToken.getAccessToken(), byRefreshToken.getRefreshToken());
            }
            else{
                tokenRepository.save(byRefreshToken);
                jwtProvider.sendAccessToken(response, byRefreshToken.getAccessToken());
            }

        } else {
            throw new IllegalArgumentException("Unexpected token");
        }
    }

    public Member checkAccessTokenValid(String accessToken){

        if(jwtProvider.validateToken(accessToken)) {
            Token byAccessToken = tokenRepository.findByAccessToken(accessToken);
            // 아이디 추출
            String userEmail = byAccessToken.getMemberEmail();
            log.info("userEmail : " + userEmail);

            Member byUserEmail = memberRepository.findByEmail(userEmail);
            log.info("member : " + byUserEmail);

            return byUserEmail;
        }
        else{
            return null;
        }
    }

    private List<GrantedAuthority> getAuthoritiesForUser(Member byUserEmail) {
        // 예시: 데이터베이스에서 사용자의 권한 정보를 조회하는 로직을 구현
        // member 객체를 이용하여 데이터베이스에서 사용자의 권한 정보를 조회하는 예시로 대체합니다.
        Role role = byUserEmail.getRole();

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" +role.name()));
        log.info("role : " + role.name());
        log.info("authorities : " + authorities);
        return authorities;
    }
}
