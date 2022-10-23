package com.zhfvkq.dyshop;

import com.zhfvkq.dyshop.domain.Member;
import com.zhfvkq.dyshop.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Controller
public class MainController {

    private final MemberService memberService;

    // 메인 페이지
    @GetMapping("/")
    public String mainPageNoneLogin(Model model) {

        Optional<Member> user = memberService.getMember("");
        model.addAttribute("user", user);

        return "index";

    }





}
