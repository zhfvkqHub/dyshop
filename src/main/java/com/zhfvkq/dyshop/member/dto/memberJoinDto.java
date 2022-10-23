package com.zhfvkq.dyshop.member.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class memberJoinDto {

    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 2, max = 10, message = "아아디는 2자 이상 10자 이하로 입력해주세요.")
    private String userId;

    @NotBlank(message = "이름을 입력해주세요.")
    private String userName;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    private String password;

    @NotBlank(message = "성별을 선택해 주세요.")
    private String sex;

    @NotBlank(message = "이메일 주소를 입력해주세요.")
    private String email;

}
