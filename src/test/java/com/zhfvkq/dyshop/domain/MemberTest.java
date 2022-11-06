package com.zhfvkq.dyshop.domain;

import com.zhfvkq.dyshop.member.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.aspectj.weaver.ResolvedTypeMunger.Parent;import static org.assertj.core.api.Assertions.assertThat;

@Getter
@AllArgsConstructor
class Parent {
    private final String parentName;
    private final int parentAge;
}

@Getter
 class Child extends Parent {
    private final String childName;
    private final int childAge;

    @lombok.Builder
    public Child(String parentName, int parentAge, String childName, int childAge) {
        super(parentName, parentAge);
        this.childName = childName;
        this.childAge = childAge;
    }
}

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    public void builder() {
        String name = "testID";
        String pwd = "hahaha!!";

        Member member = Member.builder()
                .name(name)
                .password(pwd)
                .build();

//        assertThat(member).isNotNull();

        Child child = Child.builder()
                .parentName("Andrea")
                .parentAge(38)
                .childName("Emma")
                .childAge(6)
                .build();
    }



    @Test
    public void javaBuild() {
        //given
        String name = "testID";
        String pwd = "hahaha!!";

        //when
        Member member = new Member();
        member.setName(name);
        member.setPassword(pwd);

        //then
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getPassword()).isEqualTo(pwd);
    }

}