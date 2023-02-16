package com.zhfvkq.dyshop.security.ajax;

import com.zhfvkq.dyshop.security.AccountContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AjaxAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 인증에 관련된 검증 처리
     * @param authentication the authentication request object.
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        // 사용자가 입력한 정보
        String username = authentication.getName();
        String password = (String)authentication.getCredentials();

        // DB에서 가져온 정보
        AccountContext accountContext = (AccountContext)userDetailsService.loadUserByUsername(username);

        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

        // 비밀번호 일치하지 않음
        if(!passwordEncoder.matches(password, accountContext.getMember().getPassword())){
            throw new BadCredentialsException("BadCredentialsException");
        }

        // 정책에 따라 추가적인 검증 가능


        // 최종적인 인증 객체
        return new AjaxAuthenticationToken(accountContext.getMember(), null, accountContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 토큰 타입과 파라미터 타입이 일치하면 인증 처리.
        return authentication.equals(AjaxAuthenticationToken.class);
    }
}