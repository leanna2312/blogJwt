package com.example.blogjwt.articles.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.springframework.http.HttpStatus.*;


import com.example.blogjwt.articles.service.ArticlesService;
import com.example.blogjwt.articles.dto.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ArticlesController {

    private final ArticlesService articlesService;


    // 게시글 작성
    @PostMapping("/bulletin-boards")
    public ArticleResponseDto write(@RequestBody ArticleRequestDto boardForm, HttpServletRequest request) {
        return articlesService.create(boardForm, request);
    }

    // 전체 게시글 조회
    @GetMapping("/bulletin-boards")
    public List<ArticleResponseDto> readAll() {
        return articlesService.readAll();
    }
    // 선택 게시글 조회
    @GetMapping("/bulletin-boards/{id}")
    public ArticleResponseDto readOne(@PathVariable Long id) {

        ArticleResponseDto board = articlesService.readOne(id);

        return new ArticleResponseDto(board);
    }

    // 선택 게시글 수정
    @PatchMapping("/bulletin-boards/{id}")
    public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody ArticleRequestDto boardForm, HttpServletRequest request) {
        resultFst message = null;
        try {
            message = articlesService.update(id, boardForm, request);
        }
        catch (IllegalAccessException e) {
            System.out.println("작성자만 삭제/수정할 수 있습니다.");
        }
        catch (RuntimeException e) {
            System.out.println("토큰이 유효하지 않습니다.");
        }

        if (message.getSuccess() == false) {
            return new ResponseEntity<>(message, UNAUTHORIZED);
        }

        return new ResponseEntity<>(message, OK);
    }

    // 선택 게시글 삭제
    @DeleteMapping("/bulletin-boards/{id}")
    public ResponseEntity<Object> remove(@PathVariable Long id, HttpServletRequest request) {
        resultSec resultDto = null;
        try {
            resultDto = articlesService.softDelete(id, request);
        } catch (IllegalAccessException e) {
           System.out.println("작성자만 삭제/수정할 수 있습니다.");
        }
        catch (RuntimeException e) {
            System.out.println("토큰이 유효하지 않습니다.");
        }

        return new ResponseEntity<>(resultDto, OK);
    }
}

