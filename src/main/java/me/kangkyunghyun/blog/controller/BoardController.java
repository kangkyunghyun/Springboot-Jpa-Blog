package me.kangkyunghyun.blog.controller;

import me.kangkyunghyun.blog.config.auth.PrincipalDetail;
import me.kangkyunghyun.blog.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping({"", "/"})
    public String index(Model model) {
        model.addAttribute("boards", boardService.글목록());
        return "index";
    }

    // USER 권한이 필요
    @GetMapping("/board/saveForm")
    public String saveForm() {
        return "board/saveForm";
    }
}
