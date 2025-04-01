package jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter @Setter
public class Order {

    @Id @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)//OneToMany는 Eager가 기본이므로 Lazy로 설정해주자.
    @JoinColumn(name="member_id")
    private Member member;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) //OneToMany는 Lazy가 기본
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name="delivery_id")
    private Delivery delivery;
    private LocalDateTime orderDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 (ORDER, CANCEL)

    //연관관계 메서드
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }
    //연관관계 메서드 END

    //생성 메서드 START
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems) {
        //
        Order order = new Order();

        //
        order.setMember(member);
        order.setDelivery(delivery);
        for(OrderItem oi : orderItems) {
            order.addOrderItem(oi);
        }
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    //비즈니스 로직 START
    /*
    * 주문 취소
    */
    public void cancel() {
        if( delivery.getStatus() == DeliveryStatus.COMP ) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }

        this.setStatus(OrderStatus.CANCEL);
        for(OrderItem oi : orderItems) {
            oi.cancel();
        }
    }

    //조회 로직 START
    /**
     * 전체 주문 가격 조회
     * @return
     */
    public int getTotalPrice() {
        int totalPrice = 0;
        for(OrderItem oi : orderItems) {
            totalPrice += oi.getTotalPrice();
        }
        return totalPrice;

//        return orderItems.stream().mapToInt(OrderItem::getTotalPrice).sum();
    }

}
