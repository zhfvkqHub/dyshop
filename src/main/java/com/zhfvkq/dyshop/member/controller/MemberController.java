package com.zhfvkq.dyshop.member.controller;

import com.zhfvkq.dyshop.domain.Member;
import com.zhfvkq.dyshop.member.dto.MemberJoinForm;
import com.zhfvkq.dyshop.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm(@RequestParam(required = false) String error,
                            Model model){

        model.addAttribute("user", new LoginForm());
        return "member/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("login") @Valid LoginForm loginForm, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request,
                        Model model){

        if(bindingResult.hasErrors()) return "member/login";

        Member loginMember = memberService.login(loginForm.getUserId(), loginForm.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
            return "member/login";
        }

        model.addAttribute("user",loginForm);

        return "redirect:" + redirectURL;
    }

    @GetMapping("/signup")
    public String signupForm(Model model){
        model.addAttribute("user", new MemberJoinForm());
        return "member/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("user") MemberJoinForm memberJoinForm, BindingResult bindingResult){

        log.info("user = {}",memberJoinForm.getUserId());

        if(!memberJoinForm.getUserId().equals(memberJoinForm.getConfirmId())){ // 중복 확인 아이디와 입력 아이디가 다름
            bindingResult.reject("notConfirm");
        }
        if(!memberJoinForm.getPassword().equals(memberJoinForm.getRePassword())){ // 비밀번호와 비밀번호 확인 값이 다름
            bindingResult.reject("notPassword");
        }

        if(bindingResult.hasErrors()) {
            log.info("bindingResult = {} " , bindingResult);
            return "member/signup";
        }

        // 회원가입
        memberService.memberJoin(memberJoinForm);
        return "redirect:/member/login";
    }
}
