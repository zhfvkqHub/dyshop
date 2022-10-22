package com.zhfvkq.dyshop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MainController {

    // 메인 페이지
    @GetMapping("/")
    public String mainPageNoneLogin(Model model) {
        // 로그인을 안 한 경우
//        List<Item> items = itemService.allItemView();
//        model.addAttribute("items", items);

        return "index";
    }

    // 메인 페이지 (로그인 유저) - 판매자, 구매자 로 로그인
//    @GetMapping("/main")
//    public String mainPage(Model model, @AuthenticationPrincipal PrincipalDetails principalDetails) {
//        if(principalDetails.getUser().getRole().equals("ROLE_SELLER")) {
//            // 어드민, 판매자
//            List<Item> items = itemService.allItemView();
//            model.addAttribute("items", items);
//            model.addAttribute("user", principalDetails.getUser());
//
//            return "/seller/mainSeller";
//        } else {
//            // 일반 유저일 경우
//            List<Item> items = itemService.allItemView();
//            model.addAttribute("items", items);
//            model.addAttribute("user", principalDetails.getUser());
//
//            return "user/mainUser";
//        }
//    }

}
