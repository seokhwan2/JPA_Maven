package helloJpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {
    public static void main(String[] args) {
        //persistence.xml에 있는 persistence-unit name 호출
        //EntityManagerFactory : 하나만 생성해서 애플리케이션 전체에서 공유
        //
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        //EntityManager : 쓰레드간에 공유 X (사용하고 버려야한다)
        //JPA의 모든 데이터 변경은 트랜잭션 안에서 실행
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //Member를 불러와서 em에 넣어준다.
        //member ID랑 Name에 값을 넣어준다.
        try {
            //Member findMember = em.find(Member.class, 1L);
            //findMember.setName("HelloJPA");

            //JPQL : 엔티티 객체를 조회하는 객체지향 쿼리다. 테이블을 대상으로 쿼리하는 것이 아니라 엔티티 객체를 대상으로 쿼리한다.
            //JPA는 SQL을 추상화한 JPQL이라는 객체 지향 쿼리 언어 제공
            //SQL과 문법 유사, SELECT, FROM, WHERE, GROUP, BY, HAVING, JOIN 지원
            //JPQL은 엔티티 객체를 대상으로 쿼리
            //SQL은 데이터베이스 테이블을 대상으로 쿼리
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(5)
                    .setMaxResults(8)
                    .getResultList();

            for (Member member : result)
                System.out.println("member.getName" + member.getName());
                System.out.println("###################");

            //커밋 필수 !
            tx.commit();

        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
            emf.close();
    }
}
