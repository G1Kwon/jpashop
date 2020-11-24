package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    //@Rollback(false)
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("Kwon");

        //when
        Long savedId = memberService.join(member);

        //then
        em.flush();
        assertEquals(member, memberRepository.findById(savedId));
    }

    @Test(expected = IllegalStateException.class)
    public void 입중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("Kwon");

        Member member2 = new Member();
        member2.setName("Kwon");

        //when
        memberService.join(member1);
        memberService.join(member2); // 예외가 발생해야 한다.
//        try {
//            memberService.join(member2); // 예외가 발생해야 한다. !
//        } catch (IllegalStateException e) {
//            return;
//        } expected = 덕분에 생략가능
        //then
        fail("예외가 발생해야 한다.");
    }

}