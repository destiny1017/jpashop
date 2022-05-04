package jpabook.jpashop.domain;

import jpabook.jpashop.domain.enums.DeliveryStatus;
import jpabook.jpashop.domain.items.Address;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Delivery {

    @Id @GeneratedValue
    private Long id;

    private Order order;

    private Address address;

    private DeliveryStatus status;
}
