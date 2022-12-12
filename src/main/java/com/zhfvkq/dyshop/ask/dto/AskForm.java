package com.zhfvkq.dyshop.ask.dto;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.File;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AskForm {

    @NotBlank(message = "이메일을 입력해주세요.")
    @Email(message = "올바른 이메일 주소를 입력해주세요.")
    private String email;

    @NotBlank(message = "휴대폰 번호를 입력해주세요.")
    private String phoneNumber;

    private String gateType; //문의 유형

    private String dtlType; //세부 유형 (코드 사용?)

    private String cnfrm; //확인 사항

    @NotBlank(message = "문의 내용을 입력해주세요.")
    private String contactDetails; //문의 내용

    private File atchdFile; // 첨부파일

}
