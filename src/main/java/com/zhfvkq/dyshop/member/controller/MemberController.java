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
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult,
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
    public String signup(@Valid MemberJoinForm memberJoinForm, BindingResult bindingResult){

        return "member/signup";
    }

    /**
     * 아이디 중복 채크
     */
    @GetMapping("/{userChkId}/exists")
    @ResponseBody
    public ResponseEntity<Boolean> checkUserIdDuplicate(@PathVariable String userChkId){
        log.info("userChkId == {}", userChkId);
        return ResponseEntity.ok(memberService.checkUserIdDuplicate(userChkId));
    }
}
