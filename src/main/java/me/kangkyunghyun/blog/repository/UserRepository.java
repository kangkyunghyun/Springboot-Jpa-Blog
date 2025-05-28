package me.kangkyunghyun.blog.repository;

import me.kangkyunghyun.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // 생략 가능하다.
public interface UserRepository extends JpaRepository<User, Integer> {
}
