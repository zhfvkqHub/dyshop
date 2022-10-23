package com.zhfvkq.dyshop.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@RequestMapping("/member")
@Controller
public class MemberController {

    @GetMapping("/login")
    public String loginForm(){
        return "member/login";
    }

    @GetMapping("/signup")
    public String singupForm(){
        return "member/signup";
    }
}
