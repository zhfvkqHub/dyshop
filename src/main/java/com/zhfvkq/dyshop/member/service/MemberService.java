package com.zhfvkq.dyshop.member.service;

import com.zhfvkq.dyshop.domain.Member;
import com.zhfvkq.dyshop.domain.Role;
import com.zhfvkq.dyshop.member.dto.MemberJoinForm;
import com.zhfvkq.dyshop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     * 아이디 중복 체크
     */
    public boolean checkUserIdDuplicate(String userChkId) {
        return memberRepository.existsByUserId(userChkId);
    }


    /**
     * 회원가입
     * @param member
     * @return
     */
    public String memberJoin(MemberJoinForm member) {

        Member memberEntity = new Member();
        memberEntity.memberJoin(member.getUserId(), member.getUserName(), passwordEncoder.encode(member.getPassword()), member.getEmail(), Role.USER);

        memberRepository.save(memberEntity);

        return memberEntity.getUserId();
    }

    /**
     * @return null이면 로그인 실패
     */
    public Member login(String userId, String password) {

        return memberRepository.findByUserId(userId)
                .filter(m -> passwordEncoder.matches(m.getPassword(),password))
                .orElse(null);
    }
}
