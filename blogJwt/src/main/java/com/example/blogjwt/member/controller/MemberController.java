package com.example.blogjwt.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

import com.example.blogjwt.member.dto.MemberAuthDto;
import com.example.blogjwt.member.dto.LoginRequestDto;
import com.example.blogjwt.member.dto.RegisterRequestDto;
import com.example.blogjwt.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/auth/signup")
    public ResponseEntity<MemberAuthDto> signUp(@RequestBody @Valid RegisterRequestDto signUpRequestDto) {
        String message;
        try {
            message = memberService.signUp(signUpRequestDto);
        }
        catch (IllegalArgumentException e) {
            MemberAuthDto authMessage = new MemberAuthDto("중복된 username 입니다.", BAD_REQUEST.value());
            return new ResponseEntity<>(authMessage, BAD_REQUEST);
        }
        MemberAuthDto authMessage = new MemberAuthDto(message, OK.value());

        return new ResponseEntity<>(authMessage, OK);
    }

    @PostMapping("auth/login")
    public ResponseEntity<MemberAuthDto> login(@RequestBody LoginRequestDto loginDto, HttpServletResponse response) {
        String memberToken;

        try {
            memberToken = memberService.login(loginDto);
        }
        catch (IllegalArgumentException e) {
            MemberAuthDto authMessage = new MemberAuthDto("회원을 찾을 수 없습니다.", BAD_REQUEST.value());
            return new ResponseEntity<>(authMessage, BAD_REQUEST);
        }
        response.addHeader("Authorization", memberToken);

        MemberAuthDto authMessage = new MemberAuthDto("로그인 성공", OK.value());
        return new ResponseEntity<>(authMessage, OK);
    }
}
