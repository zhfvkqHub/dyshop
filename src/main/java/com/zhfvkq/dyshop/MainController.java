package com.zhfvkq.dyshop;

import com.zhfvkq.dyshop.domain.Member;
import com.zhfvkq.dyshop.member.SessionConst;
import com.zhfvkq.dyshop.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

@RequiredArgsConstructor
@Slf4j
@Controller
public class MainController {

    private final MemberService memberService;

    @GetMapping("/")
    public String mainPageNoneLogin(Model model) {

//        // 세션에 회원 데이터가 없으면 Home
//        if(loginMember == null) return "home";
//        // 세션 유지되면 로그인으로 이동
//        model.addAttribute("mamber", loginMember);

        return "index";
    }





}
