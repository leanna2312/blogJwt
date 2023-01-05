package com.example.blogjwt.articles.dto;


import com.example.blogjwt.articles.entity.Articles;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@NoArgsConstructor
public class ArticleResponseDto {

    private Long id;
    private String title;
    private String body;
    private String username;
    private LocalDateTime createAt;
    private LocalDateTime modifiedAt;
    private Boolean isDeleted;

    public ArticleResponseDto(Articles board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.username = board.getUsername();
        this.body = board.getBody();
        this.createAt = board.getCreateAt();
        this.modifiedAt = board.getModifiedAt();
        this.isDeleted = board.getIsDeleted();
    }

    public ArticleResponseDto(ArticleResponseDto board) {
        this.id = board.getId();
        this.title = board.getTitle();
        this.username = board.getUsername();
        this.body = board.getBody();
        this.createAt = board.getCreateAt();
        this.modifiedAt = board.getModifiedAt();
        this.isDeleted = board.getIsDeleted();
    }

}
