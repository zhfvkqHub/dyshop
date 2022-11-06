package com.zhfvkq.dyshop.member.service;

import com.zhfvkq.dyshop.domain.Member;
import com.zhfvkq.dyshop.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("호출 성공 ㅡㅡ^");

        Optional<Member> member = memberRepository.findByUserId(username);

        if(member == null){
            throw new UsernameNotFoundException("UsernameNotFoundException");
        }

        String role = "";
        if(member.isPresent()){
            role = String.valueOf(member.get().getRole());
        }

        List<GrantedAuthority> roles = new ArrayList<>();
        roles.add(new SimpleGrantedAuthority(role));


        MemberContext memberContext = new MemberContext(member.get(), roles);

        return memberContext;
    }

}
