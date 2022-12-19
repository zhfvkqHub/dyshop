package com.zhfvkq.dyshop.ask.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class AskForm {

    @NotBlank(message = "empty.email")
    @Email(message = "invalid.email")
    private String email;

    @NotBlank(message = "empty.phoneNumber")
    private String phoneNumber;

    private String gateType; //문의 유형

    private String dtlType; //세부 유형 (코드 사용?)

    private String cnfrm; //확인 사항

    @NotBlank(message = "empty.ask")
    private String ask; //문의 내용

    private MultipartFile atchdFile; // 첨부파일

    @Builder

    public AskForm(String email, String phoneNumber, String gateType, String dtlType, String cnfrm, String ask, MultipartFile atchdFile) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.gateType = gateType;
        this.dtlType = dtlType;
        this.cnfrm = cnfrm;
        this.ask = ask;
        this.atchdFile = atchdFile;
    }
}
