package com.zhfvkq.dyshop;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class MainController {

    @GetMapping("/")
    public String mainPageNoneLogin() {
        return "index";
    }

    @GetMapping("/file")
    public String file() {
        return "file";
    }
    @GetMapping("/paging")
    public String mainPage() {
        return "member/paging";
    }

}
