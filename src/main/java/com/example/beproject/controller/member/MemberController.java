package com.example.beproject.controller.member;

import com.example.beproject.controller.member.Response.ResponseMember;
import com.example.beproject.domain.jwt.token.ResponseToken;
import com.example.beproject.domain.jwt.token.Token;
import com.example.beproject.domain.member.CreateMember;
import com.example.beproject.domain.member.LoginMember;
import com.example.beproject.domain.member.Member;
import com.example.beproject.domain.member.UpdateMember;
import com.example.beproject.service.member.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
@Tag(name = "MEMBER", description = "회원")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/")
    @Tag(name = "MEMBER")
    @Operation(summary = "회원가입", description = "회원가입 API")
    public ResponseEntity<?> join(@Validated @RequestBody CreateMember member,
                                  BindingResult result) {

        try{
            if (result.hasErrors()) {
                log.info("BindingResult error : " + result.hasErrors());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getClass().getSimpleName());
            }

            // 이메일 유효성 검사 추가
            if (!isValidEmail(member.getEmail())) {
                result.rejectValue("email", "email.invalid", "유효한 이메일 주소를 입력하세요.");
                log.info("Invalid email address: " + member.getEmail());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getClass().getSimpleName());
            }

            Member savedMember = memberService.register(member);

            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseMember.of(savedMember));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 회원 정보 수정
    @PutMapping("/id")
    @Tag(name = "MEMBER")
    @Operation(summary = "회원 정보 수정", description = "회원 정보 수정 API")
    public ResponseEntity<?> updateMember(@PathVariable Long id, @Validated @RequestBody UpdateMember updateMember, BindingResult result) {

        try {
            if (result.hasErrors()) {
                log.info("BindingResult error: " + result.getAllErrors());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getAllErrors());
            }

            // 해당 ID 존재 확인
            Member existingMember = memberService.findById(id);

            if (existingMember == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("회원을 찾을 수 없습니다.");
            }

            // 회원정보 업데이트
            existingMember.setNickname(updateMember.getNickname());
            existingMember.setEmail(updateMember.getEmail());
            existingMember.setPassword(updateMember.getPassword());

            // 업데이트된 Member 저장
            Member updatedMember = memberService.update(existingMember);

            return ResponseEntity.ok().body(ResponseMember.of(updatedMember));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    // 로그인
    @PostMapping("/login")
    @Tag(name = "MEMBER")
    @Operation(summary = "로그인", description = "로그인 시 JWT 반환")
    public ResponseEntity<?> login(@RequestBody LoginMember login) {
        try {
            String email = login.getEmail();
            String password = login.getPassword();
            Token token = memberService.login(email, password);

            return ResponseEntity.ok().body(ResponseToken.of(token));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("로그인을 실패했습니다.");
        }
    }

    @PostMapping("/logout")
    @Tag(name = "MEMBER")
    @Operation(summary = "로그인", description = "로그아웃")
    public ResponseEntity<?> logout(HttpServletRequest request, @AuthenticationPrincipal UserDetails user) {
        String res = memberService.logout(request, user.getUsername());
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/")
    @Tag(name = "MEMBER")
    @Operation(summary = "SpringSecurity 로그인", description = "인증 및 인가 테스트용 로그인")
    public ResponseEntity<ResponseMember> getMember(@AuthenticationPrincipal UserDetails user) {

        String email = user.getUsername();
        ResponseMember member = memberService.getByEmail(email);

        return ResponseEntity.ok().body(member);
    }

    @GetMapping("/test")
    @Tag(name = "TEST")
    @Operation(summary = "테스트", description = "성공 시 SUCCESS 반환")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok().body("SUCCESS");
    }

    // 이메일 유효성 검사 메서드
    private boolean isValidEmail(String email) {
        // 이메일 주소의 유효성을 검사하는 정규 표현식
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        return email.matches(emailRegex);
    }
}
