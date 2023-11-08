package com.example.beproject.service.member;

import com.example.beproject.controller.member.Response.ResponseMember;
import com.example.beproject.domain.jwt.JwtProvider;
import com.example.beproject.domain.jwt.token.Token;
import com.example.beproject.domain.member.CreateMember;
import com.example.beproject.domain.member.Member;
import com.example.beproject.domain.member.MemberStatus;
import com.example.beproject.domain.member.Role;
import com.example.beproject.entity.member.MemberEntity;
import com.example.beproject.entity.token.TokenEntity;
import com.example.beproject.exception.MemberNotFoundException;
import com.example.beproject.repository.member.MemberRepository;
import com.example.beproject.repository.token.TokenJpaRepository;
import com.example.beproject.repository.token.TokenRepository;
import jdk.jshell.spi.ExecutionControl;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Builder
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;
    private final JwtProvider jwtProvider;

    private final PasswordEncoder encoder;

    @Override
    public String encodePassword(String password) {
        // BCrypt 알고리즘을 사용하여 패스워드를 암호화
        return encoder.encode(password);
    }
    @Override
    public boolean matchesPassword(String rawPassword, String encodedPassword) {
        // 암호화된 패스워드와 입력한 패스워드가 일치하는지 체크
        return encoder.matches(rawPassword, encodedPassword);
    }


    @Override
    @Transactional
    public Member register(CreateMember member) {   // 유효성 검사 (예 : 이메일 중복 체크)

        // email 중복 검사
        if (memberRepository.findByEmail(member.getEmail()) != null) {
            throw new RuntimeException("이미 등록된 이메일 주소입니다.");
        }

        String password = encodePassword(member.getPassword());

        // CreateMember 객체 -> Member 엔티티로 변환
        Member newMember = Member.builder()
                .email(member.getEmail())
                .password(password)
                .nickname(member.getNickname())
                .status(MemberStatus.ACTIVE)
                .build();

        // Member 저장
        return memberRepository.save(newMember);
    }

    @Override
    public Token login(String email, String pw) {
        try{
            Member member = memberRepository.findByEmail(email);

            if(member != null) {
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, pw);
                List<GrantedAuthority> authoritiesForUser = getAuthoritiesForUser(member);

                Token findToken = tokenRepository.findByMemberEmail(email);

                // JWT 생성
                Token token = jwtProvider.createToken(authentication, authoritiesForUser);

                if (findToken == null) {
                    log.info("발급한 토큰이 없습니다. 새로운 토큰을 발급합니다.");
                } else {
                    log.info("이미 발급한 토큰이 있습니다. 토큰을 업데이트합니다.");
                    token = Token.builder()
                            .id(findToken.getId())
                            .grantType(token.getGrantType())
                            .accessToken(token.getAccessToken())
                            .accessTokenTime(token.getAccessTokenTime())
                            .refreshToken(token.getRefreshToken())
                            .refreshTokenTime(token.getRefreshTokenTime())
                            .memberEmail(token.getMemberEmail())
                            .build();

                }
                return tokenRepository.save(token);
            }
            else{
                throw new MemberNotFoundException();
            }
        }
        catch (Exception e) {
            throw e;
        }
    }

    @Override
    public ResponseMember getByEmail(String email) {
        Member findMember = memberRepository.findByEmail(email);
        if(findMember != null)
            return ResponseMember.of(findMember);
        else
            throw new MemberNotFoundException();
    }

    // 회원의 권한을 GrantedAuthority타입으로 반환하는 메소드
    private List<GrantedAuthority> getAuthoritiesForUser(Member member) {
        Role memberRole = member.getRole();
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + memberRole.name()));
        log.info("role : " + authorities);
        return authorities;
    }

    @Override
    public Member update(Member member) {
        return memberRepository.save(member);
    }

    @Override
    public Member findById(Long id) {
        return memberRepository.findById(id).orElse(null);
    }
}
