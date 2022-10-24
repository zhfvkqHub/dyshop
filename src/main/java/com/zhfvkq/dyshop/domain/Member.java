package com.zhfvkq.dyshop.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Member {

    @Id
    @Column(name="user_id")
    private String userId;
    @Column(name="user_name")
    private String name;
    private String password;
    private int misspwdCnt; // 비밀번호 오류 횟수
    private String email;
    private String role;
    private LocalDateTime address;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;


//    @Embedded
//    private Address address;

    @OneToMany(mappedBy = "member") // 연관 관계의 주인은 FK랑 가까운 컬럼에 지정 (= member)
    private List<Order> orders = new ArrayList<>();
}
