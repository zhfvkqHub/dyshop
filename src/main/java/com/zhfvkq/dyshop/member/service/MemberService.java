package com.zhfvkq.dyshop.member.service;

import com.zhfvkq.dyshop.domain.Member;
import com.zhfvkq.dyshop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public boolean checkUserIdDuplicate(String userChkId) {
        return memberRepository.existsById(userChkId);
    }

    public Optional<Member> getMember(String userId){
        return memberRepository.findById(userId);
    }
}
