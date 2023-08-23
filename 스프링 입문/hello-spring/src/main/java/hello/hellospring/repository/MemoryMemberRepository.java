package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.*;

public class MemoryMemberRepository implements MemberRepository{

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0;   // 키값 생성

    @Override
    public Member save(Member member) {

        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

    @Override
    public Optional<Member> findById(Long id) {

        return Optional.ofNullable(store.get(id));

    }

    @Override
    public Optional<Member> findByName(String name) {

        // name과 일치하는 value 값을 가지는 객체 리턴
        // 찾았는데 없으면 옵셔널이기 때문에 null 반환
        return store.values().stream()
                .filter(member -> member.getName().equals(name))
                .findAny();
    }

    @Override
    public List<Member> findAll() {
        // 멤버 객체 전부 반환
        return new ArrayList<>(store.values());
    }

    // 저장소 초기화
    public void clearStore(){

        store.clear();
    }
}
