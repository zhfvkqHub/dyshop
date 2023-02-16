package com.zhfvkq.dyshop.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 인가 처리 예외 (권한 거부)
 */
@Component
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private String errorPage;
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        String deniedUrl = errorPage + "?exception=" + accessDeniedException.getMessage();
        response.sendRedirect(deniedUrl);
    }

    public void setErrorPage(String errorPage){
        this.errorPage = errorPage;
    }

//    @GetMapping("/denied")
//    public String accessDenied(@RequestParam(value="exception", required = false) String exception,
//                               Model model){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Member member = (Member)authentication.getPrincipal();
//        model.addAttribute("username", member.getUsername());
//
//        return "/login"
//    }
}


