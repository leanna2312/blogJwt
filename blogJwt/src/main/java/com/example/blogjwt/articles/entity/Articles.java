package com.example.blogjwt.articles.entity;

import com.example.blogjwt.articles.dto.ArticleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


import com.example.blogjwt.Timestamped;
import com.example.blogjwt.member.entity.Member;

@Entity
@Getter
@NoArgsConstructor
public class Articles extends Timestamped {

    @Id
    @Column(name = "BULLETIN_BOARD_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String body;
    private Boolean isDeleted;

    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    //@OneToMany(mappedBy = "bulletinBoard", fetch = FetchType.EAGER)
    //private List<Comment> comments = new ArrayList<>();

    public Articles(ArticleRequestDto boardForm, Member member) {
        this.title = boardForm.getTitle();
        this.body = boardForm.getContents();
        this.member = member;
        this.isDeleted = null;
    }

    public void update(ArticleRequestDto boardForm) {
        this.title = boardForm.getTitle();
        this.body = boardForm.getContents();
    }
    public String getUsername() {
        return this.member.getUsername();
    }

    public void softDelete(Boolean bool) {
        this.isDeleted = bool;
    }
}
