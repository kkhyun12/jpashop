package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional // test에 존재 시 테스트가 끝나면 롤백해버림
    @Rollback(false)
    public void testMember() throws Exception {
        //given:: 어떤 사항이 주어졌을 때(해당 데이터를 기반)
        Member member = new Member();
        member.setUsername("memberB");

        //when:: ~ 실행할 때(검증할 것을 실행)
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveId);

        //then:: 검증한 결과가 ~가 나와야함
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member);

        System.out.println("findMember == member :: " + (findMember == member) );

    }

}