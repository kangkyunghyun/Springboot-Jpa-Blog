
package me.kangkyunghyun.blog.service;

import me.kangkyunghyun.blog.dto.ReplySaveRequestDto;
import me.kangkyunghyun.blog.model.Board;
import me.kangkyunghyun.blog.model.Reply;
import me.kangkyunghyun.blog.model.RoleType;
import me.kangkyunghyun.blog.model.User;
import me.kangkyunghyun.blog.repository.BoardRepository;
import me.kangkyunghyun.blog.repository.ReplyRepository;
import me.kangkyunghyun.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BoardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Autowired
    private GeminiService geminiService;

    @Transactional
    public void 글쓰기(Board board, User user) {
        String summary = geminiService.getSummary(board.getContent());
        String newContent = "[AI 3줄 요약]\n" + summary + "\n\n\n" + board.getContent();
        if (newContent != null) {
            newContent = newContent.replaceAll("(\r\n | \r | \n | \n\r)", "<br/>");
        }
        board.setCount(0);
        board.setUser(user);
        board.setContent("[AI 3줄 요약]\n" + summary + "\n\n\n" + board.getContent());
        int savedId = boardRepository.save(board).getId();
        String aiReply = geminiService.generateReply(board.getContent());
        replyRepository.mSave(0, savedId, aiReply);
    }

    @Transactional(readOnly = true)
    public Page<Board> 글목록(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Board 글상세보기(int id) {
        return boardRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
                });
    }

    @Transactional
    public void 글삭제하기(int id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void 글수정하기(int id, Board requestBoard) {
        Board board = boardRepository.findById(id)
                .orElseThrow(()->{
                    return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
                }); // 영속화 완료
        board.setTitle(requestBoard.getTitle());
        board.setContent(requestBoard.getContent());
        // 해당 함수 종료시에 트랜잭션이 Service가 종료될 때 트랜잭션이 종료됨. 이때 더티체킹 -> 자동 업데이트 DB로 flush
    }

    @Transactional
    public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {

//        User user = userRepository.findById(replySaveRequestDto.getUserId()).orElseThrow(()->{
//            return new IllegalArgumentException("댓글 쓰기 실패 : 유저 id를 찾을 수 없습니다." + replySaveRequestDto.getUserId());
//        });
//
//        Board board = boardRepository.findById(replySaveRequestDto.getBoardId()).orElseThrow(()->{
//            return new IllegalArgumentException("댓글 쓰기 실패 : 게시글 id를 찾을 수 없습니다.");
//        });
//
//        Reply reply = Reply.builder()
//                .user(user)
//                .board(board)
//                .content(replySaveRequestDto.getContent())
//                .build();
//
//        replyRepository.save(reply);

        replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
    }

    @Transactional
    public void 댓글삭제(int replyId) {
        replyRepository.deleteById(replyId);
    }
}
