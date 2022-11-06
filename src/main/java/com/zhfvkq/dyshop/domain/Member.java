package com.zhfvkq.dyshop.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Member{

//    @GeneratedValue
//    private Long id;
    @Id
    @Column(name="user_id")
    private String userId;
    @Column(name="user_name")
    private String name;
    private String password;
    private int misspwdCnt; // 비밀번호 오류 횟수
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    private LocalDateTime createDate;
    private LocalDateTime updateDate;

    @OneToMany(mappedBy = "member") // 연관 관계의 주인은 FK랑 가까운 컬럼에 지정 (= member)
    private List<Order> orders = new ArrayList<>();

    @Builder
    public Member(String userId, String name, String password, String email, Role role) {
        this.userId = userId;
        this.name = name;
        this.password = password;
        this.email = email;
        this.role = role;
        this.misspwdCnt = 0;
        this.createDate = LocalDateTime.now();
    }




}
