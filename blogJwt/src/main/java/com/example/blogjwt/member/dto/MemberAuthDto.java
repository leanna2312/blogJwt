package com.example.blogjwt.member.dto;


import lombok.Getter;

@Getter
public class MemberAuthDto {

    private String msg;
    private Integer httpStatus;

    public MemberAuthDto(String msg, Integer httpStatus) {
        this.msg = msg;
        this.httpStatus = httpStatus;
    }
}
