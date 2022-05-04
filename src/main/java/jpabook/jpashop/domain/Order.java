package jpabook.jpashop.domain;

import jpabook.jpashop.domain.enums.OrderStatus;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity
public class Order {

    @Id @GeneratedValue
    private String id;

    private Member member;

    private Delivery delivery;


    private LocalDateTime date;
    private OrderStatus status;

}
