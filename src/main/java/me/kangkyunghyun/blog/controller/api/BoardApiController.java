package me.kangkyunghyun.blog.controller.api;

import me.kangkyunghyun.blog.config.auth.PrincipalDetail;
import me.kangkyunghyun.blog.dto.ResponseDto;
import me.kangkyunghyun.blog.model.Board;
import me.kangkyunghyun.blog.model.RoleType;
import me.kangkyunghyun.blog.model.User;
import me.kangkyunghyun.blog.service.BoardService;
import me.kangkyunghyun.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardApiController {

    @Autowired
    private BoardService boardService;

    @PostMapping("/api/board")
    public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
        boardService.글쓰기(board, principal.getUser());
        return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
    }
}
