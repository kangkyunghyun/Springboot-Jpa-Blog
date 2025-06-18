package me.kangkyunghyun.blog.controller;

import me.kangkyunghyun.blog.config.auth.PrincipalDetail;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @GetMapping({"", "/"})
    public String index(@AuthenticationPrincipal PrincipalDetail principalDetail) {
        System.out.println("로그인 사용자 아이디: " + principalDetail.getUsername());
        return "index";
    }
}
