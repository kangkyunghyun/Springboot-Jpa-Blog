package me.kangkyunghyun.blog.repository;

import me.kangkyunghyun.blog.model.Board;
import me.kangkyunghyun.blog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // 생략 가능하다.
public interface BoardRepository extends JpaRepository<Board, Integer> {
}