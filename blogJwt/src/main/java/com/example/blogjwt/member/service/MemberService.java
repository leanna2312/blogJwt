package com.example.blogjwt.member.service;

import com.example.blogjwt.jwt.JwtUtil;
import com.example.blogjwt.member.dto.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Optional;
import com.example.blogjwt.articles.*;
import com.example.blogjwt.articles.dto.*;
import com.example.blogjwt.member.dto.RegisterRequestDto;
import com.example.blogjwt.member.entity.Member;
import com.example.blogjwt.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtUtil jwtUtil;

    private final String SIGNUP_SUCCESS = "회원가입 성공";

    public String signUp(RegisterRequestDto requestDto) {
        if (memberRepository.findByUsername(requestDto.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 유저이름입니다.");
        }

        Member signUpMember = new Member(requestDto);
        memberRepository.save(signUpMember);

        return SIGNUP_SUCCESS;
    }

    public String login(LoginRequestDto loginDto) {
        Optional<Member> findMember = memberRepository.findByUsername(loginDto.getUsername());

        if (findMember.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 사용자입니다.");
        }

        Member presentMember = findMember.orElseThrow();

        if (!presentMember.getPassword().equals(loginDto.getPassword())) {
            throw new IllegalArgumentException("아이디/비밀번호가 일치하지 않습니다.");
        }

        return jwtUtil.createToken(presentMember.getUsername());
    }
}
