package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("member1");
        //when
        Long id = memberService.join(member);
        //then
        assertEquals(member, memberRepository.findOne(id));

    }

    @Test
    public void 중복_회원_예외() throws Exception {
        Assertions.assertThrows(IllegalStateException.class, () -> {
            //given
            Member member1 = new Member();
            Member member2 = new Member();
            member1.setName("member1");
            member2.setName("member1");
            //when
            memberService.join(member1);
            memberService.join(member2);
            //then
        }, "예외가 발생하지 않았습니다.");
    }

}