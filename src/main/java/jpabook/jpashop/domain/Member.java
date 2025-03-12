package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name="member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member") //읽기전용이 된다. Member에 따라 ?
    private List<Order> orders = new ArrayList<>(); //필드에서 바로 초기화하는것이 안전
    /*
    컬렉션은 필드에서 바로 초기화 하는 것이 안전.
    - null excpetion
    - 하이버네이트는 엔티티를 영속화 할 때, 컬랙션을 감싸서 하이버네이트가 제공하는 내장 컬렉션으로 변경한다.
    */

}
