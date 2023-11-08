package com.example.beproject.config;

import com.example.beproject.domain.jwt.*;
import com.example.beproject.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.DelegatingPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
// @EnableGlobalMethodSecurity 어노테이션은 Spring Security에서 메서드 수준의 보안 설정을 활성화하는데
// 사용되는 어노테이션입니다.
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final JwtService jwtService;
    private final JwtExceptionFilter jwtExceptionFilter;

    private static final String[] PERMIT_URL_ARRAY = {
            /* swagger v3 */
            "/v3/api-docs/**",
            "/swagger-ui/**"
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .disable()
                .csrf().disable()
                .formLogin().disable()
                .logout().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http
                .authorizeRequests()

                .antMatchers("/api/member").permitAll()
                .antMatchers("/api/member/**").permitAll()
                .antMatchers(PERMIT_URL_ARRAY).permitAll()
                .antMatchers(HttpMethod.POST,"/api/member/**").permitAll()
                .antMatchers(HttpMethod.PUT,"/api/member/**").permitAll()
                .antMatchers(HttpMethod.DELETE,"/api/member/**").permitAll()
                .antMatchers("/api/post").permitAll()
                .antMatchers(HttpMethod.POST,"/api/post/**").permitAll()
                .antMatchers(HttpMethod.PUT,"/api/post/**").permitAll()
                .antMatchers(HttpMethod.DELETE,"/api/post/**").permitAll()
                .antMatchers("/api/comment").permitAll()
                .antMatchers(HttpMethod.POST,"/api/comment/**").permitAll()
                .antMatchers(HttpMethod.PUT,"/api/comment/**").permitAll()
                .antMatchers(HttpMethod.DELETE,"/api/comment/**").permitAll()

                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider, jwtService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter,JwtAuthenticationFilter.class);

        http
                .exceptionHandling()
                .accessDeniedHandler(new JwtAccessDeniedHandler());


        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        String idForEncode = "bcrypt";
        Map<String, PasswordEncoder> encoders = new HashMap<>();
        encoders.put(idForEncode, new BCryptPasswordEncoder());
        return new DelegatingPasswordEncoder(idForEncode, encoders);
    }
}
