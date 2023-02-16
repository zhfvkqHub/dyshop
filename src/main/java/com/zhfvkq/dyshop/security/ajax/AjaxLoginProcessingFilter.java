package com.zhfvkq.dyshop.security.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhfvkq.dyshop.domain.Member;
import com.zhfvkq.dyshop.security.ajax.AjaxAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxLoginProcessingFilter extends AbstractAuthenticationProcessingFilter {

    public AjaxLoginProcessingFilter(){
        super(new AntPathRequestMatcher("/api/login","POST"));
    }

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {

        if(!isAjax(request)){
            throw new IllegalStateException("Ajax 요청이 아님");
        }

        Member member = objectMapper.readValue(request.getReader(), Member.class);

        if(StringUtils.isEmpty(member.getUserId()) || StringUtils.isEmpty(member.getPassword())){
            throw new IllegalStateException("아이디 또는 비밀번호가 존재하지 않습니다.");
        }

        AjaxAuthenticationToken ajaxAuthenticationToken =
                new AjaxAuthenticationToken(member.getUserId(), member.getPassword());

        return getAuthenticationManager().authenticate(ajaxAuthenticationToken);
    }

    private boolean isAjax(HttpServletRequest request){
        if("XMLHttpRequest".equals(request.getHeader("X-Requested-with"))){
            return true;
        }
        return false;
    }
}
