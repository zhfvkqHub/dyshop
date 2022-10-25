package com.zhfvkq.dyshop.member.controller;

import com.zhfvkq.dyshop.member.SessionConst;
import com.zhfvkq.dyshop.member.dto.MemberJoinForm;
import com.zhfvkq.dyshop.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/login")
    public String loginForm(Model model){
        model.addAttribute("login", new LoginForm());
        return "member/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("member") @Valid LoginForm form, BindingResult bindingResult,
                        @RequestParam(defaultValue = "/") String redirectURL,
                        HttpServletRequest request){

        if(bindingResult.hasErrors()) return "member/login";

        // 로그인 성공
        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, "");;

        return "redirect:" + redirectURL;
    }

    @GetMapping("/signup")
    public String signupForm(Model model){
        model.addAttribute("member", new MemberJoinForm());
        return "member/signup";
    }

    @PostMapping("/signup")
    public String signup(@Valid @ModelAttribute("member") MemberJoinForm memberJoinForm, BindingResult bindingResult){

        log.info("member = {}",memberJoinForm.getUserId());

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
