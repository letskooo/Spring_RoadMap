package com.example.jwt.repository;

import com.example.jwt.model.APIUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<APIUser, Integer> {

    // findBy 규칙 -> Username 문법.
    // select * from user where username = ?
    // 저 물음표에는 파라미터 값이 들어가는 쿼리가 자동 생성.
    public APIUser findByUsername(String username);
}
