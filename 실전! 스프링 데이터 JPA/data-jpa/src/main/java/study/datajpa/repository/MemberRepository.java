package study.datajpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import study.datajpa.entity.Member;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> { // 엔티티 타입과 그것의 키

    List<Member> findByUsername(String username);

}
