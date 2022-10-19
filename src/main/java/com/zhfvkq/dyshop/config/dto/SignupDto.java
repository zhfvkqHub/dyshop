package com.zhfvkq.dyshop.config.dto;

import com.zhfvkq.dyshop.config.entity.User;
import lombok.Data;

@Data
public class SignupDto {
    private String username;
    private String pwd;
    private String email;

    public User toEntity() {
        return User.builder()
                .username(username)
                .pwd(pwd)
                .email(email)
                .build();
    }
}