package com.example.beproject.controller.member;

import com.example.beproject.controller.member.Response.ResponseMember;
import com.example.beproject.domain.member.CreateMember;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "회원", description = "회원 API입니다.")
@Slf4j
@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

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

            // Service return
            ResponseMember responseMember = new ResponseMember();

            return ResponseEntity.status(HttpStatus.CREATED).body(responseMember);
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
