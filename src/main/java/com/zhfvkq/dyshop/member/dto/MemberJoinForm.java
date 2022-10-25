package com.zhfvkq.dyshop.member.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class MemberJoinForm {

    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 2, max = 10, message = "아아디는 2자 이상 10자 이하로 입력해주세요.")
    private String userId;

    @NotBlank(message = "이름을 입력해주세요.")
    private String userName;

    @NotBlank(message = "비밀번호를 입력해주세요.")
//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$", message = "비밀번호는 8~16자리수여야 합니다. 영문 대소문자, 숫자, 특수문자를 1개 이상 포함해야 합니다.")
    private String password;

//    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$", message = "비밀번호는 8~16자리수여야 합니다. 영문 대소문자, 숫자, 특수문자를 1개 이상 포함해야 합니다.")
    private String rePassword;

    @NotBlank(message = "성별을 선택해 주세요.")
    private String sex;

    @NotBlank(message = "이메일 주소를 입력해주세요.")
    private String email;

//    @NotBlank(message = "년도를 입력해주세요.")
//    @Size(min = 4,max = 4,message = "년도는 4자리로 입력해주세요.")
//    private String yy;
//
//    @Size(min = 1,max = 2,message = "월은 2자리로 입력해주세요.")
//    @NotBlank(message = "월을 입력해주세요.")
//    private String mm;
//
//    @Size(min = 1,max = 2,message = "일은 2자리로 입력해주세요.")
//    @NotBlank(message = "일을 입력해주세요.")
//    private String dd;

    @NotBlank(message = "아이디 중복 확인을 진행해주세요.")
    private String confirmId; // 중복 체크된 아이디 (userId = confirmId 되야함)

}
