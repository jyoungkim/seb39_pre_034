package com.codestates.stackoverflowclone.member.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.NotBlank;

@Getter
public class MemberSignupDto {

    @NotBlank(message = "이름은 공백이 아니어야 합니다.")
    private String username;

    private String email;

    private String password;
}
