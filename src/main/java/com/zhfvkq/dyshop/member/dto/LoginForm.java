package com.zhfvkq.dyshop.member.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginForm {

    @NotBlank(message = "empty.id")
    private String userId;
    @NotBlank(message = "empty.password")
    private String password;
    private boolean idMaintain;
}
