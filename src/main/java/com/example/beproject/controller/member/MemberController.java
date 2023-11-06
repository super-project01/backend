package com.example.beproject.controller.member;

import com.example.beproject.controller.member.Response.ResponseMember;
import com.example.beproject.domain.jwt.token.ResponseToken;
import com.example.beproject.domain.jwt.token.Token;
import com.example.beproject.domain.member.CreateMember;
import com.example.beproject.domain.member.LoginMember;
import com.example.beproject.repository.member.MemberRepository;
import com.example.beproject.service.member.MemberService;
import io.swagger.models.Response;
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

@Tag(name = "회원", description = "회원 API입니다.")
@Slf4j
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/")
    @Tag(name = "member")
    @Operation(summary = "회원가입", description = "회원가입하는 API입니다")
    public ResponseEntity<?> join(@Validated @RequestBody CreateMember member,
                                  BindingResult result) {
        try{
            if (result.hasErrors()) {
                log.info("BindingResult error : " + result.hasErrors());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result.getClass().getSimpleName());
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(null);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    // 로그인
    @PostMapping("/login")
    @Tag(name = "member")
    @Operation(summary = "로그인 API", description = "로그인을 하면 JWT를 반환해줍니다.")
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

    @GetMapping("/")
    public ResponseEntity<ResponseMember> getMember(@AuthenticationPrincipal UserDetails user) {

        String email = user.getUsername();
        ResponseMember member = memberService.getByEmail(email);

        return ResponseEntity.ok().body(member);
    }

    @GetMapping("/test")
    public ResponseEntity<?> test() {
        return ResponseEntity.ok().body("SUCCESS");
    }

}
