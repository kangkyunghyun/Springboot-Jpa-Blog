package me.kangkyunghyun.blog.repository;

import me.kangkyunghyun.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository // 생략 가능하다.
public interface UserRepository extends JpaRepository<User, Integer> {
    // JPA Naming 쿼리
    // SELECT * FROM user WHERE username = ?1 AND password = ?2;
    User findByUsernameAndPassword(String username, String password);

//    @Query(value = "SELECT * FROM user WHERE username = ?1 AND password = ?2", nativeQuery = true)
//    User login(String username, String password);
}
