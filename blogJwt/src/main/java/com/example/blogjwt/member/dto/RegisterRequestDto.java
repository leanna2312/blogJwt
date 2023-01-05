package com.example.blogjwt.member.dto;

import com.example.blogjwt.member.entity.MemberGrant;



import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;


@Data
@NoArgsConstructor
public class RegisterRequestDto {

    @NotBlank
    @NotNull
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-z]).{4,10}")
    private String username;
    @NotBlank
    @NotNull
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[~!@#$%^&*()_+=]).{8,15}")
    private String password;

    private MemberGrant authRange = MemberGrant.USER;

    private String adminToken = "";

}
