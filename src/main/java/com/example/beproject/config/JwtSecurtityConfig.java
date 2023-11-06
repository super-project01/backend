package com.example.beproject.config;

import com.example.beproject.domain.jwt.JwtAuthenticationFilter;
import com.example.beproject.domain.jwt.JwtProvider;
import com.example.beproject.service.jwt.JwtService;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class JwtSecurtityConfig extends
        SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtProvider jwtProvider;
    private final JwtService jwtService;

    public JwtSecurtityConfig(JwtProvider jwtProvider, JwtService jwtService) {
        this.jwtProvider = jwtProvider;
        this.jwtService = jwtService;
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        // JwtAuthenticationFilter가 일반 로그인에 대한 토큰 검증을 처리
        JwtAuthenticationFilter cutomFilter = new JwtAuthenticationFilter(jwtProvider, jwtService);
        builder.addFilterBefore(cutomFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
