package com.example.blogjwt.articles.service;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;


import com.example.blogjwt.articles.dto.*;
import com.example.blogjwt.articles.entity.Articles;
import com.example.blogjwt.articles.repository.ArticlesRepository;
import com.example.blogjwt.jwt.JwtUtil;
import com.example.blogjwt.member.entity.Member;
import com.example.blogjwt.member.repository.MemberRepository;

import static com.example.blogjwt.member.entity.MemberGrant.*;

@Service
@RequiredArgsConstructor
public class ArticlesService {

    private final ArticlesRepository articlesRepository;
    private final MemberRepository memberRepository;

    private final JwtUtil jwtUtil;

    public ArticleResponseDto create(ArticleRequestDto boardForm, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);

        if (token == null) {
            return null;
        }

        if (!jwtUtil.validateToken(token)) {
            return null;
        }

        Claims claims = jwtUtil.getUserInfoFromToken(token);
        Member member = memberRepository.findByUsername(claims.getSubject()).orElseThrow();

        Articles board = new Articles(boardForm, member);
        Articles saveBoard = articlesRepository.save(board);

        return new ArticleResponseDto(saveBoard);
    }

    public List<ArticleResponseDto> readAll() {
        List<Articles> boards = articlesRepository.findAllByOrderByCreateAtDesc()
                .stream().filter(bulletinBoard -> bulletinBoard.getIsDeleted() == null).collect(Collectors.toList());

        return (List<ArticleResponseDto>) boards.stream().map(bulletinBoard -> new ArticleResponseDto(bulletinBoard));

    }

    public ArticleResponseDto readOne(Long id) {
        Articles board = articlesRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        if (board.getIsDeleted() != null) {
            throw new IllegalArgumentException("삭제된 게시글입니다.");
        }

        return new ArticleResponseDto(board);
    }

    @Transactional
    public resultSec softDelete(Long id, HttpServletRequest request) throws IllegalAccessException {
        Articles board = articlesRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        String token = jwtUtil.resolveToken(request);
        Claims claims = jwtUtil.getUserInfoFromToken(token);
        Member member = memberRepository.findByUsername(claims.getSubject()).orElseThrow();

        if (token == null) {
            return null;
        }

        if (!jwtUtil.validateToken(token) || member.getGrade().equals(ADMIN)) {
            return null;
        }

        if (!board.getMember().getId().equals(member.getId())) {
            throw new IllegalAccessException("작성자만 삭제/수정할 수 있습니다.");
        }

        board.softDelete(true);

        return new resultSec(true);
    }

    @Transactional
    public resultFst update(Long id, ArticleRequestDto boardForm, HttpServletRequest request) throws IllegalAccessException {
        Articles board = articlesRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        String token = jwtUtil.resolveToken(request);
        Claims claims = jwtUtil.getUserInfoFromToken(token);
        Member member = memberRepository.findByUsername(claims.getSubject()).orElseThrow();

        if (token == null) {
        }

        if (!jwtUtil.validateToken(token) || member.getGrade().equals(ADMIN)) {
        }

        if (!board.getMember().getId().equals(member.getId())) {
            throw new IllegalAccessException("작성자만 삭제/수정할 수 있습니다.");
        }

        board.update(boardForm);

        return new resultFst(true, new ArticleResponseDto(board));
    }
}
