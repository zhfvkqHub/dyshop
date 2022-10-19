package com.zhfvkq.dyshop.config.controller;

import com.zhfvkq.dyshop.config.entity.User;
import com.zhfvkq.dyshop.config.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class AuthController {

    private final AuthService authService;

    @GetMapping("/signin")
    public String SigninForm() {
        return "signin";
    }

    @GetMapping("/signup")
    public String SignupForm() {
        return "signup";
    }

    @PostMapping("/signup")
    public String signUp(User user) {

        User newUser = user; //새로운 유저 받음

        User userEntity = authService.signup(user);
        System.out.println(userEntity);

        return "signin";
    }

    // 로그인성공 창에서 로그아웃 버튼
    //@RequestMapping(value="logout", method = RequestMethod.GET)
    @GetMapping("/logout")
    public String logout(HttpSession session) throws Exception {
        authService.logout(session);
        return "redirect:/signin";
    }
}