package jpabook.jpashop.service;

import jpabook.jpashop.domain.Address;
import jpabook.jpashop.domain.Item;
import jpabook.jpashop.domain.Member;
import jpabook.jpashop.domain.Order;
import jpabook.jpashop.domain.enums.OrderStatus;
import jpabook.jpashop.domain.items.Book;
import jpabook.jpashop.exception.NotEnoughStockException;
import jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.springframework.test.util.AssertionErrors.assertEquals;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    private EntityManager em;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        //given
        Member member = createMember();
        Item item = createBook();

        //when
        int orderCnt = 3;
        Long orderId = orderService.order(member.getId(), item.getId(), orderCnt);

        //then
        Order getOrder = em.find(Order.class, orderId);
        assertEquals("상품 주문 시 상태는 ORDER", getOrder.getStatus(), OrderStatus.ORDER);
        assertEquals("주문한 상품 종류 수가 정확해야함", getOrder.getOrderItems().size(), 1);
        assertEquals("주문 가격은 가격 * 수량이다", getOrder.getTotalPrice(), item.getPrice() * orderCnt);
        assertEquals("주문 수량만큼 재고가 줄어야함", item.getStockQuantity(), 7);

    }

    private Book createBook() {
        Book book = new Book();
        book.setName("췍췍");
        book.setAuthor("어허어허");
        book.setIsbn("978899326643");
        book.setPrice(80000);
        book.setStockQuantity(10);
        em.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("김멤버");
        member.setAddress(new Address("서울", "곰달래로", "51"));
        em.persist(member);
        return member;
    }

    @Test
    public void 상품주문_재고수량초과() throws Exception {
        Assertions.assertThrows(NotEnoughStockException.class, () -> {
            //given
            Member member = createMember();
            Item item = createBook();
            int orderCnt = 11;

            //when
            orderService.order(member.getId(), item.getId(), orderCnt);

            //then
        }, "재고 수량 부족 예외가 발생해야함");
    }

    @Test
    public void 상품주문취소() throws Exception {
        //given

        //when

        //then

    }
}