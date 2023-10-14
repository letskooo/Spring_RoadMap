package study.datajpa.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class Member {

    @Id
    @GeneratedValue
    private Long id;
    private String username;

    // JPA는 기본 생성자가 있어야 하는데
    // 다른 곳에서 호출되지 않도록 protected 접근자 설정
    protected Member(){

    }

    public Member(String username){
        this.username = username;
    }
}
