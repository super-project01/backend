package com.example.beproject.domain.jwt;

import com.example.beproject.domain.member.Member;
import com.example.beproject.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String[] whitelist = {"/api/member/login","/api/member/join", "/api/member/logout","/api/member/login/**","/api/member/join/**",
                                                "/api/member/test","/swagger-ui","/swagger-ui/*", "/swagger-resources/**"};
    private final String TEST_NONCHECK_URL = "/";
    private final JwtProvider jwtProvider;
    private final JwtService jwtService;

    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if(checkPathFree(request.getRequestURI())){
            filterChain.doFilter(request, response);
            return;
        }

        // 개발용 SpringSecurity 인증/인가 제외하기
        if(request.getRequestURI().contains(TEST_NONCHECK_URL)){
            filterChain.doFilter(request, response);
            return;
        }

        String refreshToken = jwtProvider.extractRefreshToken(request)
                .filter(jwtProvider::validateToken)
                .orElse(null);

        // refreshToken이 담아져서 오는 경우 accesstoken재발급
        if (refreshToken != null) {
            jwtService.createAccessTokenHeader(response, refreshToken);
            return;
        }
        else{
            //refreshToken이 없다면 accessToken을 재발급하고 인가
            checkAccessTokenAndAuthentication(request, response, filterChain);
        }
    }

    public boolean checkPathFree(String requestURI){
        return PatternMatchUtils.simpleMatch(whitelist, requestURI);
    }

    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                  FilterChain filterChain) throws ServletException, IOException {
        log.info("checkAccessTokenAndAuthentication() 호출");
        String accessToken = jwtProvider.extractAccessToken(request)
                .filter(jwtProvider::validateToken).orElse(null);

        Member member = jwtService.checkAccessTokenValid(accessToken);
        if(member != null){
            this.saveAuthentication(member);
        }

        filterChain.doFilter(request, response);
    }

    public void saveAuthentication(Member myUser) {
        String password = myUser.getPassword();

        UserDetails userDetailsUser = org.springframework.security.core.userdetails.User.builder()
                .username(myUser.getEmail())
                .password(password)
                .roles(myUser.getRole().name())
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetailsUser, null,
                        authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
