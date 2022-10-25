package com.zhfvkq.dyshop.member.service;

import com.zhfvkq.dyshop.domain.Member;
import com.zhfvkq.dyshop.domain.Role;
import com.zhfvkq.dyshop.member.dto.MemberJoinForm;
import com.zhfvkq.dyshop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public String memberJoin(MemberJoinForm member) {
        Encrypt en = new Encrypt();
        String salt = en.getSalt();
        String encryptPassword = en.getEncrypt(member.getPassword(), salt);

//        LocalDateTime date = LocalDateTime.parse("20000328");

        Member memberEntity = new Member();
        memberEntity.memberJoin(member.getUserId(), member.getUserName(), encryptPassword,member.getEmail(), Role.ROLE_USER);

        memberRepository.save(memberEntity);

        return memberEntity.getUserId();
    }
}
