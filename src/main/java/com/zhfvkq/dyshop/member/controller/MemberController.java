package com.zhfvkq.dyshop.member.controller;

import com.zhfvkq.dyshop.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm(){
        return "member/login";
    }

    @GetMapping("/signup")
    public String singupForm(Model model){
        return "member/signup";
    }

    /**
     * 아이디 중복확인
     */
    @GetMapping("/member/{userChkId}/exists")
    public ResponseEntity<Boolean> checkUserIdDuplicate(@PathVariable String userChkId){
        return ResponseEntity.ok(memberService.checkUserIdDuplicate(userChkId));
    }
}
