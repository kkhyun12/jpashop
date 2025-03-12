package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name="delivery_id")
    private Long id;

    @OneToOne(mappedBy = "delivery", fetch = LAZY )
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    //ORDINAL의 경우 숫자로 들어가는데, 중간에 상태값이 추가되면 숫자가 밀리기 때문에 사용x
    private DeliveryStatus status;//READY, COMP
}
