package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor //final있는 필드만 가지고 생성자를 만들어준다.
public class MemberService {

    /*
    1.
    @Autowired
    private MemberRepository memberRepository;
    단점은 변경이 힘들다.

    2.
    //setter injection

    private MemberRepository memberRepository;
    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    장점은 테스트코드 작성 시 값을 넣어볼수 있다. (메소드를 이용해서 주입을 하니까)
    단점은 실제 애플리케이션 돌아가는 시점에 셋팅이 끝났기때문에 setter를 이용해서 바꿀일이 없다.

    3.
    생성자 injection을 사용 권장
     */
    private final MemberRepository memberRepository;

    /**
     * 회원가입
     * @param member
     * @return
     */
    @Transactional(readOnly = false)
    public Long join(Member member) {
        validateDuplicateMember(member); //중복회원 조회 검증
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if( !findMembers.isEmpty() ) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //회원 조회
    public Member findMember(Long id) {
        return memberRepository.findOne(id);
    }


}
