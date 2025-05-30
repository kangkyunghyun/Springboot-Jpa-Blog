package me.kangkyunghyun.blog.test;

import org.springframework.web.bind.annotation.*;

// 사용자가 요청 -> 응답(HTML 파일)
// @Controller

// 사용자가 요청 -> 응답(Data)
@RestController
public class HttpControllerTest {

    private static final String TAG = "HttpControllerTest : ";

    @GetMapping("/http/lombok")
    public String lombokTest() {
        Member m = Member.builder().username("ssar").password("1234").email("ssar@gmail.com").build();
        System.out.println(TAG + "getter : " + m.getUsername());
        m.setUsername("13213");
        System.out.println(TAG + "setter : " + m.getUsername());
        return "lombok test 완료";
    }

    // 인터넷 브라우저 요청은 무조건 get 요청밖에 할 수 없다
    @GetMapping("/http/get")
    public String getTest(Member m) {
        return "get 요청 : " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
    }

    @PostMapping("/http/post") // text/plain, application/json
    public String postTest(@RequestBody Member m) { // MessageConverter (스프링부트)
        return "post 요청 : " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
    }

    @PutMapping("/http/put")
    public String putTest(@RequestBody Member m) {
        return "put 요청" + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
    }

    @DeleteMapping("/http/delete")
    public String deleteTest() {
        return "delete 요청";
    }
}
