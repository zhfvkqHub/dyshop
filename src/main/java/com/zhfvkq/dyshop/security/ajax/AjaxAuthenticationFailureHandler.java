package com.zhfvkq.dyshop.security.ajax;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhfvkq.dyshop.domain.Member;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 로그인 실패 시 반환 Json
 */
public class AjaxAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        String errMsg = "로그인 실패";

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        // 인증에서 온 에러
        if(exception instanceof BadCredentialsException){
            errMsg = "Invalid Username or Password";
        }else if(exception instanceof InsufficientAuthenticationException){
            errMsg = "Invalid Secret Key";
        }

        objectMapper.writeValue(response.getWriter(), errMsg);
    }
}
