package com.zhfvkq.dyshop.member.controller;

import com.zhfvkq.dyshop.member.SessionConst;
import com.zhfvkq.dyshop.member.dto.MemberForm;
import com.zhfvkq.dyshop.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;


@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm(){
        return "member/login";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute MemberForm form, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request){
        if(bindingResult.hasErrors()) return "member/login";

        // 로그인 성공
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, "");;

        return "redirect:" + redirectURL;
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
