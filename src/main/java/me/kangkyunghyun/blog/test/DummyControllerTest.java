package me.kangkyunghyun.blog.test;

import jakarta.transaction.Transactional;
import me.kangkyunghyun.blog.model.RoleType;
import me.kangkyunghyun.blog.model.User;
import me.kangkyunghyun.blog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Supplier;

// html 파일이 아니라 data를 리턴해주는 controller = RestController
@RestController
public class DummyControllerTest {

//    @PostMapping("/dummy/join")
//    public String join(String username, String password, String email) {
//        System.out.println("username: " + username);
//        System.out.println("password: " + password);
//        System.out.println("email: " + email);
//        return "회원가입이 완료되었습니다.";
//    }

    @Autowired // 의존성 주입
    private UserRepository userRepository;

    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable int id) {
        try {
            userRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
        }
        return "삭제되었습니다. id: " + id;
    }

    // save 함수는 id를 전달하지 않으면 insert를 해주고
    // save 함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
    // save 함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 해준다
    @Transactional // 함수 종료 시 자동으로 commit 됨
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User requestUser) { // json 데이터를 요청 => Java Object(MessageConverter의 Jackson 라이브러리가 변환해서 받아줌)
        System.out.println("id: "+ id);
        System.out.println("pw: "+requestUser.getPassword());
        System.out.println("em: "+requestUser.getEmail());

        User user = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("수정에 실패했습니다.");
        });
        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());
//        userRepository.save(user);
        return user;
    }

    @GetMapping("/dummy/users")
    public List<User> list() {
        return userRepository.findAll();
    }

    // 한페이지당 2건의 데이터를 리턴
    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size = 2, sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
      Page<User> pagingUser = userRepository.findAll(pageable);
      List<User> users = pagingUser.getContent();
      return users;
    }

    // {id} 주소로 파라미터를 전달 받을 수 있음
    // http://localhost:8000/blog/dummy/user/3
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id) {
        // 데이터베이스에서 찾지 못하면 user가 null이 됨
        // null값이 return 되므로 Optional로 User 객체를 감싸서 null인지 판단하도록 함
        User user = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("해당 사용자는 없습니다.");
        });
        // 요청 : 웹브라우저
        // user 객체 = 자바 오브젝트
        // 변환 (웹브라우저가 이해할 수 있는 데이터) -> json
        // 스프링부트 = MessageConverter가 응답시 자동 작동
        // 만약 자바 오브젝트를 리턴하면 MessageConverter가 Jackson 라이브러리 호출하고
        // user 오브젝트를 json으로 변환해서 브라우저에게 던짐
        return user;
    }

    @PostMapping("/dummy/join")
    public String join(User user) {
        System.out.println("id: " + user.getId());
        System.out.println("username: " + user.getUsername());
        System.out.println("password: " + user.getPassword());
        System.out.println("email: " + user.getEmail());
        System.out.println("role: " + user.getRole());
        System.out.println("createDate: " + user.getCreateDate());

        user.setRole(RoleType.USER);
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }
}
