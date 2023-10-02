package com.example.security1.repository;

import com.example.security1.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    // findBy 규칙 -> Username 문법.
    // select * from user where username = ?
    // 저 물음표에는 파라미터 값이 들어가는 쿼리가 자동 생성.
    public User findByUsername(String username);
}
