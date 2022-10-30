package com.zhfvkq.dyshop;

import com.zhfvkq.dyshop.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Slf4j
@Controller
public class MainController {

    private final MemberService memberService;

    @GetMapping("/")
    public String mainPageNoneLogin(Model model) {

        return "index";
    }


}
