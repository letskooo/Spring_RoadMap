package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();  // 트랜잭션 얻기
        tx.begin();  // 트랜잭션 시작

        try {

            // 비영속
            Member member = new Member();
            member.setId(100L);
            member.setName("HelloJPA");

            // 영속
            System.out.println("====BEFORE====");
            em.persist(member); // em 안에 있는 영속성 컨텍스트를 통해 이 엔티티가 관리됨
            System.out.println("====AFTER====");
            tx.commit();  // 트랜잭션 커밋
        } catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
