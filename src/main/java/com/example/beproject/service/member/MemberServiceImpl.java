package com.example.beproject.service.member;

import com.example.beproject.controller.member.Response.ResponseMember;
import com.example.beproject.domain.jwt.JwtProvider;
import com.example.beproject.domain.jwt.token.Token;
import com.example.beproject.domain.member.*;
import com.example.beproject.exception.MemberNotFoundException;
import com.example.beproject.repository.member.MemberRepository;
import com.example.beproject.repository.token.TokenRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public Member updateMember(Long id, UpdateMember updateMember) {
        // id로 회원 정보를 조회
        Optional<Member> optionalMember = memberRepository.findById(id);

        if (optionalMember.isPresent()) {
            Member member = optionalMember.get();

            // 회원 정보 업데이트
            member.setNickname(updateMember.getNickname());
            member.setEmail(updateMember.getEmail());
            member.setPassword(updateMember.getPassword());

            // 회원 정보 저장 및 반환
            return memberRepository.save(member);
        }

        // 조회한 회원이 존재하지 않는 경우 null 반환
        return null;
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
                .role(Role.USER)
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

    public String logout(HttpServletRequest request, String email){
        try{
            Optional<String> refreshToken = jwtProvider.extractRefreshToken(request);
            Optional<String> accessToken = jwtProvider.extractAccessToken(request);

            Token findToken = tokenRepository.findByMemberEmail(email);
            tokenRepository.deleteById(findToken.getId());

            return "로그아웃에 성공하였습니다.";
        }
        catch (Exception e){
            return "로그아웃 시 토큰 초기화에 실패하였습니다.";
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
}
