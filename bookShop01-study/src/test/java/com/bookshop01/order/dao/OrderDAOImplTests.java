package com.bookshop01.order.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mklinkj.test.support.FixedDateTestHelper.changeNowLocalDateTime;

import com.bookshop01.order.vo.OrderVO;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@SpringJUnitConfig(locations = {"classpath:config/spring/root-context.xml"})
class OrderDAOImplTests {

  @Autowired OrderDAO orderDAO;

  /*
    오늘 주문한 내역만 조회: selectMyOrderList
    Mockito를 활용해서 LocalDateTime의 날짜를
    주문 테이블에 있는 주문 내역의 날짜로 고정해서 실행해보자!
  */
  @Test
  void listMyOrderGoods() {
    changeNowLocalDateTime(
        LocalDateTime.of(2018, 10, 23, 0, 0, 0),
        () -> {
          OrderVO orderVO = new OrderVO();
          orderVO.setMember_id("lee");
          List<OrderVO> result = orderDAO.listMyOrderGoods(orderVO);
          assertThat(result).hasSize(2);
        });
  }

  @Transactional
  @Test
  void insertNewOrder() {
    OrderVO o1 = new OrderVO();

    // 1.  order_seq_num: INSERT가 실행될 때 시퀀스로 자동 처리된다.

    // 2. 주문 아이디: 주문 아이디를 처리해주는 시퀀스의 결과값이 전달된다.
    o1.setOrder_id(10);

    // 3. 회원 아이디
    o1.setMember_id("lee");

    // 4. 상품 아이디
    o1.setGoods_id(397);

    // 5. 상품 제목
    o1.setGoods_title("리액트를 다루는 기술");

    // 6. 상품 파일명 (주문서에 상품 이미지를 위해 사용되는 것 같다.)
    o1.setGoods_fileName("main_react.jpg");

    // 7. 주문 상품 수량
    o1.setOrder_goods_qty(1);

    // 8. 상품 판매 가격
    o1.setGoods_sales_price(30_000);

    // 9. 주문자 이름
    o1.setOrderer_name("이병승");

    // 10. 받는사람 이름
    o1.setReceiver_name("이병승");

    // 11 ~ 13. 받는 사람 핸드폰 번호
    o1.setReceiver_hp1("010");
    o1.setReceiver_hp2("1111");
    o1.setReceiver_hp3("2222");

    // 14 ~ 16. 받는 사람 유선 번호
    o1.setReceiver_tel1("02");
    o1.setReceiver_tel2("3333");
    o1.setReceiver_tel3("5555");

    // 17. 배송 주소
    o1.setDelivery_address(
        "우편번호:13547<br>도로명 주소:경기 성남시 분당구 고기로 25 (동원동)<br>[지번 주소:경기 성남시 분당구 동원동 79-1]<br>럭키빌딩 101호");

    // 18. 부재시 전달 메시지
    o1.setDelivery_message("부재시 경비실에 맡겨주세요.");

    // 19. 배송 방법
    o1.setDelivery_method("일반택배");

    // 20. 상품 포장 여부
    o1.setGift_wrapping("no");

    // 21. 결제 방법
    o1.setPay_method("신용카드<Br>카드사:삼성<br>할부개월수:일시불");

    // 22. 결제 카드 회사 이름
    o1.setCard_com_name("삼성");

    // 23. 할부 개월 수
    o1.setCard_pay_month("일시불");

    // 24. 주문자 휴대폰 번호
    o1.setOrderer_hp("01011112222");

    // 25. 휴대폰 결제 번호
    o1.setPay_orderer_hp_num("해당없음");

    orderDAO.insertNewOrder(List.of(o1));
  }

  /*
    주문 아이디로 주문 조회
  */
  @Test
  void findMyOrder() {
    int orderId = 92;
    orderDAO.findMyOrder(orderId);
  }

  /** 장바구니에 저장한 상품 삭제 */
  @Transactional
  @Test
  void removeGoodsFromCart() {
    int goodsId = 340;
    orderDAO.removeGoodsFromCart(goodsId);
  }
}
