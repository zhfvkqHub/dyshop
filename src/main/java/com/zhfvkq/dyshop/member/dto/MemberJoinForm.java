package com.zhfvkq.dyshop.member.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Getter
public class MemberJoinForm {

    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 2, max = 10, message = "아아디는 2자 이상 10자 이하로 입력해주세요.")
    private String userId;

    @NotBlank(message = "이름을 입력해주세요.")
    private String userName;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    private String password;

    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    private String rePassword;

    @NotBlank(message = "성별을 선택해 주세요.")
    private String sex;

    @NotBlank(message = "이메일 주소를 입력해주세요.")
    private String email;

    @NotBlank(message = "생년월일을 입력해주세요.")
    @Size(min=8,  max = 8,message = "생년월일을 8자리로 입력해주세요.")
    private String address;

    @NotBlank(message = "아이디 중복 확인을 진행해주세요.")
    private String confirmId; // 중복 체크된 아이디 (userId = confirmId 되야함)
}
