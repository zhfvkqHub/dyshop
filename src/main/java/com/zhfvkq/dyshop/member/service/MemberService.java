package com.zhfvkq.dyshop.member.service;

import com.zhfvkq.dyshop.domain.Member;
import com.zhfvkq.dyshop.domain.Role;
import com.zhfvkq.dyshop.member.dto.MemberJoinForm;
import com.zhfvkq.dyshop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public boolean checkUserIdDuplicate(String userChkId) {
        return memberRepository.existsByUserId(userChkId);
    }

    public Optional<Member> getMember(String userId){
        return memberRepository.findByUserId(userId);
    }

    /**
     * 회원가입
     * @param member
     * @return
     */
    public String memberJoin(MemberJoinForm member) {

        // 음 .. SHA256 말고 스프링 시큐리티 암호화 사용할 예정 (일단 암호화 x)
        String encodingPassword = encoding(member.getPassword());

        Member memberEntity = new Member();
        memberEntity.memberJoin(member.getUserId(), member.getUserName(), member.getPassword(), member.getEmail(), Role.ROLE_USER);

        memberRepository.save(memberEntity);

        return memberEntity.getUserId();
    }

    /**
     * 암호화
     * @param password
     * @return
     */
    public String encoding(String password){
        Encrypt en = new Encrypt();
        String salt = en.getSalt();
        return en.getEncrypt(password, salt);
    }

    /**
     * @return null이면 로그인 실패
     */
    public Member login(String userId, String password) {

        return memberRepository.findByUserId(userId)
                .filter(m -> m.getPassword().equals(password))
                .orElse(null);
    }
}
