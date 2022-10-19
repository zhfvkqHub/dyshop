package com.zhfvkq.dyshop.config.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.boot.jdbc.DataSourceBuilder;

import javax.persistence.*;
import javax.sql.DataSource;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
public class User {

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private int id;

    @Column(name = "user_name")
    private String username;
    private String pwd;
    private int misspwd; // 비밀번호 오류 횟수
    private String email;
    private String role;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    @PrePersist // DB에 INSERT 되기 직전에 실행
    public void createDate() {
        this.createDate = LocalDateTime.now();
    }


}
