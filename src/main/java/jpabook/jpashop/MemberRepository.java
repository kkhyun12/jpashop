package jpabook.jpashop;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId(); //커맨드랑 쿼리를 분리해라 - 저장하고 나면 사이드 이펙트를 일으킬 수 있는 커맨드성이기 때문에 리턴값을 만들지 않는게 좋다.
    }

    public Member find(Long id) {
        return em.find(Member.class, id);
    }
}
