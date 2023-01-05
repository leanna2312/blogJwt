package com.example.blogjwt.member.entity;

import com.example.blogjwt.Timestamped;
import com.example.blogjwt.member.dto.RegisterRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;
import static com.example.blogjwt.member.entity.MemberGrant.USER;
import static javax.persistence.GenerationType.*;

@Entity
@Getter
@NoArgsConstructor
@Validated
public class Member extends Timestamped {

    @Id
    @Column(name = "MEMBER_ID")
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MemberGrant grade;

    public Member(RegisterRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
        this.grade = USER;
    }



}
