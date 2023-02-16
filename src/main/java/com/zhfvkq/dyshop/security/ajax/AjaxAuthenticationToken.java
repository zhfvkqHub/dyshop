package com.zhfvkq.dyshop.security.ajax;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Ajax 로그인용 토큰
 */
public class AjaxAuthenticationToken extends AbstractAuthenticationToken {

    private final Object principal;
    private Object credentials;

    // 인증을 받기전 사용자가 입력한 아이디 비번 담는 생성자
    public AjaxAuthenticationToken(Object principal, Object credentials) {
        super(null);
        this.principal = principal;
        this.credentials = credentials;
        setAuthenticated(false);
    }

    // 인증 이후 결과를 담는 생성자
    public AjaxAuthenticationToken(Object principal, Object credentials, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }
}
